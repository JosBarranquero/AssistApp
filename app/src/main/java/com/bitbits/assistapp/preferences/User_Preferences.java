package com.bitbits.assistapp.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.bitbits.assistapp.utilities.ApiClient;

/**
 * Class which saves the User name and Password on a preferences file for logging in automatically
 *
 * @author José Antonio Barranquero Fernández
 * @version 1.0
 */
public class User_Preferences {
    private static SharedPreferences sharedPreferences;
    private static final String USER_KEY = "username";
    private static final String PASS_KEY = "password";
    private static final String EMAIL_KEY = "email";
    private static final String APIKEY_KEY = "apikey";
    private static final String SOUND_KEY = "notifications_sound";
    private static final String VIBRA_KEY = "notifications_vibrate";

    /**
     * Method which saves the username in the app preferences file
     *
     * @param username
     * @param context
     */
    public static void saveUser(String username, Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_KEY, username);
        editor.apply();
    }

    /**
     * Method which saves the password in the app preferences file
     *
     * @param password
     * @param context
     */
    public static void savePass(String password, Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PASS_KEY, password);
        editor.apply();
    }

    /**
     * Method which saves the email in the app preferences file
     *
     * @param email
     * @param context
     */
    public static void saveEmail(String email, Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EMAIL_KEY, email);
        editor.apply();
    }

    /**
     * Method which saves the user apikey in yhe app preferences file
     * @param apikey
     * @param context
     */
    public static void saveApikey(String apikey, Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(APIKEY_KEY, apikey);
        editor.apply();
    }

    /**
     * Method which retrieves the username from the app preferences file
     *
     * @param context
     * @return The username
     */
    public static String getUser(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(USER_KEY, null);
    }

    /**
     * Method which retrieves the password from the app preferences file
     *
     * @param context
     * @return The password
     */
    public static String getPass(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(PASS_KEY, null);
    }

    /**
     * Method which retrieves the email from the app preferences file
     *
     * @param context
     * @return The email
     */
    public static String getEmail(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(EMAIL_KEY, null);
    }

    /**
     * Method which retrieves the user apikey from the app preferences file
     *
     * @param context
     * @return The email
     */
    public static String getApikey(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(APIKEY_KEY, ApiClient.DEFAULT_APIKEY);
    }

    /**
     * Method which retrieves the sound when a message is received option
     *
     * @param context
     * @return True for sound, false for no sound
     */
    public static boolean getSound(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(SOUND_KEY, true);
    }

    /**
     * Method which retrieves the vibration when a message is received option
     *
     * @param context
     * @return True for vibration, false for no vibration
     */
    public static boolean getVibration(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(VIBRA_KEY, true);
    }
}
