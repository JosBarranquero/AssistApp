package com.bitbits.assistapp;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.bitbits.assistapp.services.Message_Service;

/**
 * @author José Antonio Barranquero Fernández
 * @version 2.0
 *          AssistApp
 */

public class AssistApp_Application extends Application {
    public static final String URL = "https://bitbits.hopto.org/AssistApp/";
    private static final String TAG = "AssistApp";

    private static Context context;
    private static boolean isServiceRunning = false;

    @Override
    public void onCreate() {
        super.onCreate();

        context = this;
        startMessageService();
    }

    /**
     * Method which starts our service
     */
    public static void startMessageService() {
        if (!isServiceRunning) {
            Intent intent = new Intent(context, Message_Service.class);
            context.startService(intent);

            isServiceRunning = true;

            Log.d(TAG, "Starting service");
        }
    }

    /**
     * Method which stops our service
     */
    public static void stopMessageService() {
        if (isServiceRunning) {
            Intent intent = new Intent(context, Message_Service.class);
            context.stopService(intent);

            isServiceRunning = false;

            Log.d(TAG, "Stopping service");
        }
    }

    /**
     * Method which returns the application version codename
     * @return Version codename
     */
    public static String getCodename() {
        return context.getString(R.string.codename);
    }

    /**
     * Method which returns the application version
     * @return App version
     */
    public static String getVersion() {
        String version = null;
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            version = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, e.getMessage());
        }
        return version;
    }

    /**
     * Context getter
     * @return Context
     */
    public static Context getContext() {
        return context;
    }

    /**
     * Method which checks if our service is currently running
     * @return True if it's running, false if it isn't
     */
    public static boolean isServiceRunning() {
        return isServiceRunning;
    }
}
