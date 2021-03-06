package com.bitbits.assistapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bitbits.assistapp.interfaces.IAccount;
import com.bitbits.assistapp.preferences.User_Preferences;
import com.bitbits.assistapp.presenters.Login_Presenter;
import com.bitbits.assistapp.utilities.ApiClient;
import com.bitbits.assistapp.utilities.Connectivity;

/**
 * Activity which will manage the system login
 *
 * @author José Antonio Barranquero Fernández
 * @version 2.0
 */
public class Login_Activity extends AppCompatActivity implements IAccount.View {
    private Button mBtnLogin;
    private TextInputLayout mTilUser;
    private TextInputLayout mTilPassword;
    private TextInputEditText mEdtPassword;
    private TextInputEditText mEdtUser;
    private TextView mTxvForgot;

    private IAccount.Presenter mLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!Connectivity.isConnected()) {
            showNetworkError();
        } else {
            if (User_Preferences.getPass(this) != null && User_Preferences.getUser(this) != null) { //If the user has already logged in we launch the next activity
                launchActivity();
            } else {
                setContentView(R.layout.activity_login);
                mLogin = new Login_Presenter(this);

                mTilUser = (TextInputLayout) findViewById(R.id.tilUser);
                mTilPassword = (TextInputLayout) findViewById(R.id.tilPassword);

                mEdtUser = (TextInputEditText) findViewById(R.id.edtUser);
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
                mEdtPassword = (TextInputEditText) findViewById(R.id.edtPassword);
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

                mTxvForgot = (TextView) findViewById(R.id.txvForgot);
                mTxvForgot.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Login_Activity.this, Forgot_Activity.class);
                        startActivity(intent);
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
                Snackbar.make(findViewById(idView), error, Snackbar.LENGTH_SHORT).show();
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
        finish();
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
     * Method which validates the credentials, but checks if network is available beforehand
     *
     * @param user     The user name
     * @param password The password
     */
    private void validateCredentials(String user, String password) {
        if (Connectivity.isConnected()) {
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
        builder.setIcon(R.drawable.logo);
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
}
