package com.bitbits.assistapp.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.bitbits.assistapp.AssistApp_Application;

/**
 * Class which checks device's network connectivity and speed
 *
 * @author José Antonio Barranquero Fernández
 * @version 1.0
 *          AssistApp
 */
public class Connectivity {
    /**
     * Get the network info
     *
     * @return
     */
    private static NetworkInfo getNetworkInfo() {
        ConnectivityManager cm = (ConnectivityManager) AssistApp_Application.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }

    /**
     * Check if there is any connectivity
     *
     * @return
     */
    public static boolean isConnected() {
        NetworkInfo info = Connectivity.getNetworkInfo();
        return (info != null && info.isConnected());
    }

    /**
     * Check if there is any connectivity to a Wifi network
     *
     * @return
     */
    public static boolean isConnectedWifi() {
        NetworkInfo info = Connectivity.getNetworkInfo();
        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_WIFI);
    }

    /**
     * Check if there is any connectivity to a mobile network
     *
     * @return
     */
    public static boolean isConnectedMobile() {
        NetworkInfo info = Connectivity.getNetworkInfo();
        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_MOBILE);
    }
}