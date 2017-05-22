package com.bitbits.assistapp.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.bitbits.assistapp.AssistApp_Application;
import com.bitbits.assistapp.preferences.User_Preferences;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * @author José Antonio Barranquero Fernández
 * @version 1.0
 *          AssistApp
 */
public class ApiClient {
    private static final String BASE_URL = AssistApp_Application.URL + "API/";

    public static final String USERS = "user";
    public static final String MEDDATA = "meddata";
    public static final String MEDRECORD = "medrecord";
    public static final String ASSISTS = "assist";
    public static final String MESSAGES = "messages";

    public static final String DEFAULT_APIKEY = "assistapp";

    public static final int NON_ACTIVE = 419;
    public static final int NEW_VERSION = 421;
    public static final int WRONG_CREDENTIALS = 420;

    private static final int MAX_TIMEOUT = 2000;
    private static final int RETRIES = 3;
    private static final int TIMEOUT_BETWEEN_RETRIES = 500;

    private static AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);

    public static void get(String url, AsyncHttpResponseHandler responseHandler) {
        client.addHeader("apikey", User_Preferences.getApikey(AssistApp_Application.getContext()));
        client.addHeader("userId", String.valueOf(User_Preferences.getId(AssistApp_Application.getContext())));
        client.addHeader("codename", AssistApp_Application.getCodename());
        client.setTimeout(MAX_TIMEOUT);
        client.setMaxRetriesAndTimeout(RETRIES, TIMEOUT_BETWEEN_RETRIES);
        client.get(getAbsoluteUrl(url), responseHandler);
    }

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.addHeader("apikey", User_Preferences.getApikey(AssistApp_Application.getContext()));
        client.addHeader("userId", String.valueOf(User_Preferences.getId(AssistApp_Application.getContext())));
        client.addHeader("codename", AssistApp_Application.getCodename());
        client.setTimeout(MAX_TIMEOUT);
        client.setMaxRetriesAndTimeout(RETRIES, TIMEOUT_BETWEEN_RETRIES);
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.addHeader("apikey", User_Preferences.getApikey(AssistApp_Application.getContext()));
        client.addHeader("userId", String.valueOf(User_Preferences.getId(AssistApp_Application.getContext())));
        client.addHeader("codename", AssistApp_Application.getCodename());
        client.setTimeout(MAX_TIMEOUT);
        client.setMaxRetriesAndTimeout(RETRIES, TIMEOUT_BETWEEN_RETRIES);
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void put(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.addHeader("apikey", User_Preferences.getApikey(AssistApp_Application.getContext()));
        client.addHeader("userId", String.valueOf(User_Preferences.getId(AssistApp_Application.getContext())));
        client.addHeader("codename", AssistApp_Application.getCodename());
        client.setTimeout(MAX_TIMEOUT);
        client.setMaxRetriesAndTimeout(RETRIES, TIMEOUT_BETWEEN_RETRIES);
        client.put(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void delete(String url, AsyncHttpResponseHandler responseHandler) {
        client.addHeader("apikey", User_Preferences.getApikey(AssistApp_Application.getContext()));
        client.addHeader("userId", String.valueOf(User_Preferences.getId(AssistApp_Application.getContext())));
        client.addHeader("codename", AssistApp_Application.getCodename());
        client.setTimeout(MAX_TIMEOUT);
        client.setMaxRetriesAndTimeout(RETRIES, TIMEOUT_BETWEEN_RETRIES);
        client.delete(getAbsoluteUrl(url), responseHandler);
    }

    public static void cancelRequests(Context c, boolean flag) {
        client.cancelRequests(c, flag);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

    /**
     * Method which checks for internet connectivity
     *
     * @return True if it network is available, false if it is not
     */
    public static boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) AssistApp_Application.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}
