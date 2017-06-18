package com.bitbits.assistapp.interfaces;

import android.content.Context;

/**
 * Interface to be implemented by both Forgot_Activity and Forgot_Presenter
 * @author José Antonio Barranquero Fernández
 * @version 1.0
 *          AssistApp
 */

public interface IPassword {
    interface View {
        void showMessage(String message);
        Context getContext();
    }

    interface Presenter {
        void resetPassword(String email, String idDoc);
    }
}
