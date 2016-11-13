package com.bitbits.assistapp.presenters;

import android.content.Context;
import android.text.TextUtils;

import com.bitbits.assistapp.R;
import com.bitbits.assistapp.Repository;
import com.bitbits.assistapp.interfaces.IAccount;
import com.bitbits.assistapp.models.User;

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
    public boolean validateUser(String user) {
        boolean valid = false;
        if (TextUtils.isEmpty(user)) {
            view.setErrorMessage(((Context)view).getResources().getString(R.string.data_empty), R.id.edtUser);
        } else {
            valid = true;
        }
        return valid;
    }

    @Override
    public boolean validatePassword(String password) {
        String error = "";
        boolean valid = false;
        if (TextUtils.isEmpty(password)) {
            error = ((Context)view).getResources().getString(R.string.data_empty);
        } else {
            if (!(password.matches("(.*)\\d(.*)")))
                error = ((Context) view).getResources().getString(R.string.password_digit);
            if (!(password.matches("(.*)\\p{Lower}(.*)") && password.matches("(.*)\\p{Upper}(.*)")))
                error = ((Context) view).getResources().getString(R.string.password_case);
            if (password.length() < 8)
                error = ((Context) view).getResources().getString(R.string.password_length);
            else
                valid = true;
        }
        if (!valid)
            view.setErrorMessage(error, R.id.edtPassword);
        return valid;
    }

    /**
     * Method which validates the login credentials and check if they exists.
     * If they do, it launches the next activity
     * @param user        The username
     * @param password    The password
     */
    public void validateCredentials(String user, String password) {
        if (validateUser(user) && validatePassword(password)) {
            for (User account :
                    Repository.getInstance().getUser()) {
                if (account.getId_doc().equals(user) && account.getPass().equals(password) && account.isActive()) {
                    view.launchActivity(account.getName() + " " + account.getSurname());
                    Repository.getInstance().setCurrentUser(account);
                } else {
                    view.setErrorMessage(((Context)view).getString(R.string.credentials_error), R.id.edtPassword);
                }
            }
        }
    }
}
