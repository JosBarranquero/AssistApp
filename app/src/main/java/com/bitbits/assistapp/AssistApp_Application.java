package com.bitbits.assistapp;

import android.app.Application;
import android.content.Context;

/**
 * @author José Antonio Barranquero Fernández
 * @version 1.0
 *          AssistApp
 */

public class AssistApp_Application extends Application {
    private static Context context;
    public static final String URL = "https://bitbits.hopto.org/AssistApp/";

    public AssistApp_Application() {
        super();
        context = this;
    }

    public static String getCodename() {
        return context.getString(R.string.codename);
    }

    public static Context getContext() {
        return context;
    }
}
