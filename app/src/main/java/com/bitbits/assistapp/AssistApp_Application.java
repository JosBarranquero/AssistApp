package com.bitbits.assistapp;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.PowerManager;
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
     * Method which starts our service
     */
    public static void stopMessageService() {
        if (isServiceRunning) {
            Intent intent = new Intent(context, Message_Service.class);
            context.stopService(intent);

            isServiceRunning = false;

            Log.d(TAG, "Stopping service");
        }
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
            Log.e(TAG, e.getMessage());
        }
        return version;
    }

    public static Context getContext() {
        return context;
    }

    public static boolean isServiceRunning() {
        return isServiceRunning;
    }
}
