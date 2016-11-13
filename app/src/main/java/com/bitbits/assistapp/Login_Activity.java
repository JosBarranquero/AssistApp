package com.bitbits.assistapp;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bitbits.assistapp.interfaces.IAccount;
import com.bitbits.assistapp.presenters.Login_Presenter;

/**
 * Class which will manage the system login
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLogin = new Login_Presenter(this);

        mTilUser = (TextInputLayout)findViewById(R.id.tilUser);
        mTilPassword = (TextInputLayout)findViewById(R.id.tilPassword);

        mEdtUser = (EditText)findViewById(R.id.edtUser);
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
        mEdtPassword = (EditText)findViewById(R.id.edtPassword);
        mEdtPassword.addTextChangedListener(new TextWatcher() {
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

        mBtnLogin = (Button)findViewById(R.id.btnLogin);
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = mEdtUser.getText().toString();
                String password = mEdtPassword.getText().toString();
                mLogin.validateCredentials(user, password);
            }
        });
    }

    @Override
    public void setErrorMessage(String error, int idView) {
        switch (idView) {
            case R.id.edtPassword:
                mTilPassword.setError(error);
                break;
            case R.id.edtUser:
                mTilUser.setError(error);
        }
    }

    @Override
    public void launchActivity(String name) {
        Intent intent = new Intent(Login_Activity.this, ConversationList_Activity.class);
        intent.putExtra("name", name);
        startActivity(intent);
    }
}
