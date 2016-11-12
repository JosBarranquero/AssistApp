package com.bitbits.assistapp.presenters;

import android.content.Context;
import android.text.TextUtils;

import com.bitbits.assistapp.R;
import com.bitbits.assistapp.Repository;
import com.bitbits.assistapp.interfaces.IAccount;

/**
 * Presenter for Login_Activity
 * @author José Antonio Barranquero Fernández
 * @version 1.0
 */
public class Login_Presenter implements IAccount.Presenter {
    Repository data = Repository.getInstance();
    IAccount.View view;

    public Login_Presenter(IAccount.View view) {
        this.view = view;
    }

    @Override
    public void validateUser(String user) {
        if (TextUtils.isEmpty(user)) {
            view.setErrorMessage(((Context)view).getResources().getString(R.string.data_empty), R.id.edtUser);
        }
    }

    @Override
    public void validatePassword(String password) {
        String error;
        boolean valid = false;
        if (TextUtils.isEmpty(password)) {
            error = ((Context)view).getResources().getString(R.string.data_empty);
        }
    }
}
