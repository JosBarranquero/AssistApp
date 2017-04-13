package com.bitbits.assistapp.interfaces;

import android.content.Context;

/**
 * Interface to be implemented by the Login_Presenter and Login_Activity
 * @author José Antonio Barranquero Fernández
 * @version 1.0
 * @see com.bitbits.assistapp.Login_Activity
 * @see com.bitbits.assistapp.presenters.Login_Presenter
 */
public interface IAccount {
    interface View {
        void setErrorMessage(String error, int idView);

        void launchActivity();

        Context getContext();
    }

    interface Presenter {
        boolean validateUser(String user);

        boolean validatePassword(String password);

        void validateCredentials(String user, String password);
    }
}
