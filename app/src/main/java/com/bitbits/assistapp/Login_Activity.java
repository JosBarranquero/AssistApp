package com.bitbits.assistapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bitbits.assistapp.interfaces.IAccount;
import com.bitbits.assistapp.preferences.User_Preferences;
import com.bitbits.assistapp.presenters.Login_Presenter;

/**
 * Activity which will manage the system login
 *
 * @author José Antonio Barranquero Fernández
 * @version 1.0
 */
public class Login_Activity extends AppCompatActivity implements IAccount.View {
    private Button mBtnLogin;
    private TextInputLayout mTilUser;
    private TextInputLayout mTilPassword;
    private EditText mEdtPassword;
    private EditText mEdtUser;

    private IAccount.Presenter mLogin;

    private Repository mRepository = Repository.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!mRepository.isNetworkAvailable()) {
            showNetworkError();
        } else {
            setContentView(R.layout.activity_login);
            if (User_Preferences.getPass(this) != null && User_Preferences.getUser(this) != null) { //If the user has already logged in we launch the next activity
                launchActivity();
            } else {
                mLogin = new Login_Presenter(this);

                mTilUser = (TextInputLayout) findViewById(R.id.tilUser);
                mTilPassword = (TextInputLayout) findViewById(R.id.tilPassword);

                mEdtUser = (EditText) findViewById(R.id.edtUser);
                mEdtUser.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        mTilUser.setError(null);
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
                mEdtPassword = (EditText) findViewById(R.id.edtPassword);
                mEdtPassword.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        mTilPassword.setError(null);
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });

                mBtnLogin = (Button) findViewById(R.id.btnLogin);
                mBtnLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String user = mEdtUser.getText().toString();
                        String password = mEdtPassword.getText().toString();
                        validateCredentials(user, password);
                    }
                });
            }
        }
    }

    /**
     * Method which shows and error message on the corresponding EditText
     *
     * @param error  The text of the error
     * @param idView The EditText where to show the error
     */
    @Override
    public void setErrorMessage(String error, int idView) {
        switch (idView) {
            case R.id.edtPassword:
                mTilPassword.setError(error);
                break;
            case R.id.edtUser:
                mTilUser.setError(error);
                break;
            case R.id.activity_login:
                Snackbar.make(findViewById(idView), error, Snackbar.LENGTH_INDEFINITE).setAction(android.R.string.ok, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        (Login_Activity.this).finish();
                    }
                }).show();
                break;
            case 0:
                showVersionError();
                break;
        }
    }

    /**
     * Medthod which launches the Home_Activity after the credentials have been validated
     *
     * @see Home_Activity
     */
    @Override
    public void launchActivity() {
        Intent intent = new Intent(Login_Activity.this, Home_Activity.class);
        startActivity(intent);
    }

    /**
     * Method which returns the Context
     *
     * @return The context
     * @see Context
     */
    @Override
    public Context getContext() {
        return this;
    }


    /**
     * Method which validates the credential, but checks if the network is available beforehand
     *
     * @param user     The user name
     * @param password The password
     */
    private void validateCredentials(String user, String password) {
        if (mRepository.isNetworkAvailable()) {
            mLogin.validateCredentials(user, password);
        } else {
            showNetworkError();
        }
    }

    /**
     * Method which shows an AlertDialog telling the user that the application needs a network connection
     */
    private void showNetworkError() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.app_name);
        builder.setMessage(R.string.no_network);
        builder.setCancelable(false);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        builder.show();
    }

    private void showVersionError() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.app_name);
        builder.setIcon(R.drawable.logo);
        builder.setMessage(R.string.old_version);
        builder.setCancelable(false);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(AssistApp_Application.URL+"apk/AssistApp.apk"));
                startActivity(i);
            }
        });
        builder.show();
    }
}
