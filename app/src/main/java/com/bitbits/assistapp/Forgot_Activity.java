package com.bitbits.assistapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import com.bitbits.assistapp.interfaces.IPassword;
import com.bitbits.assistapp.presenters.Forgot_Presenter;

/**
 * Activity to reset a user password
 *
 * @author José Antonio Barranquero Fernández
 * @version 1.0
 *          AssistApp
 */

public class Forgot_Activity extends AppCompatActivity implements IPassword.View {
    private IPassword.Presenter mPresenter;

    private TextInputEditText mEdtDoc;
    private TextInputEditText mEdtMail;
    private TextInputLayout mTilDoc;
    private TextInputLayout mTilMail;
    private Button mBtnReset;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        setTitle(R.string.reset_password);

        mPresenter = new Forgot_Presenter(this);

        mEdtMail = (TextInputEditText) findViewById(R.id.edtEmail);
        mEdtMail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTilMail.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mEdtDoc = (TextInputEditText) findViewById(R.id.edtUser);
        mEdtDoc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTilDoc.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mTilDoc = (TextInputLayout) findViewById(R.id.tilUser);
        mTilMail = (TextInputLayout) findViewById(R.id.tilEmail);

        mBtnReset = (Button) findViewById(R.id.btnReset);
        mBtnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEdtMail.getText().toString();
                String idDoc = mEdtDoc.getText().toString();
                if (idDoc.isEmpty()) {  // If the idDoc field is empty
                    mTilDoc.setError(getString(R.string.data_empty));
                } else {
                    if (email.isEmpty()) {  // If the email field is empty
                        mTilMail.setError(getString(R.string.data_empty));
                    } else {    // If they are both filled
                        mPresenter.resetPassword(email, idDoc);
                    }
                }
            }
        });
    }

    /**
     * Method which shows a message to the user
     *
     * @param message
     */
    @Override
    public void showMessage(String message) {
        Snackbar.make(findViewById(R.id.activity_forgot), message, Snackbar.LENGTH_LONG).show();
    }

    /**
     * Context getter
     *
     * @return Context
     */
    @Override
    public Context getContext() {
        return this;
    }
}
