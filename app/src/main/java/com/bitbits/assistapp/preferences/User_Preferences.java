package com.bitbits.assistapp.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Class which saves the User name and Password on a preferences file for logging in automatically
 * @author José Antonio Barranquero Fernández
 * @version 1.0
 */
public class User_Preferences {
    private static SharedPreferences sharedPreferences;
    public static final String USER_KEY = "username";
    public static final String PASS_KEY = "password";

    public static void saveUser(String username, Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_KEY, username);
        editor.commit();
    }

    public static void savePass(String password, Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PASS_KEY, password);
        editor.commit();
    }

    public static String getUser(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(USER_KEY, null);
    }

    public static String getPass(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(PASS_KEY, null);
    }
}
