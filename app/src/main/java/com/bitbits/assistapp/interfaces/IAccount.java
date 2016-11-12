package com.bitbits.assistapp.interfaces;

/**
 * Interface to be implemented by the Login_Presenter and Login_Activity
 * @author José Antonio Barranquero Fernández
 * @version 1.0
 */
public interface IAccount {
    interface View {
        void setErrorMessage(String error, int idView);
        void launchActivity();
    }

    interface Presenter {
        void validateUser(String user);
        void validatePassword(String password);
    }
}
