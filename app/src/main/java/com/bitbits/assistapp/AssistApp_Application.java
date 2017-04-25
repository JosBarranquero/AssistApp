package com.bitbits.assistapp;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

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

    public static String getVersion() {
        String version = null;
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            version = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("AssistApp", e.getMessage());
        }
        return version;
    }

    public static Context getContext() {
        return context;
    }
}
