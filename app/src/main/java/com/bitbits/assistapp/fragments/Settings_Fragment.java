package com.bitbits.assistapp.fragments;

import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.util.Log;

import com.bitbits.assistapp.R;
import com.bitbits.assistapp.Repository;
import com.bitbits.assistapp.models.Result;
import com.bitbits.assistapp.preferences.User_Preferences;
import com.bitbits.assistapp.utilities.ApiClient;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Fragment which manages the app settings
 * @author José Antonio Barranquero Fernández
 * @version 1.0
 */
public class Settings_Fragment extends PreferenceFragment {
    EditTextPreference edtPassword;
    EditTextPreference edtEmail;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings_app);

        edtPassword = (EditTextPreference)findPreference("password");
        edtEmail = (EditTextPreference)findPreference("email");

        edtPassword.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                User_Preferences.savePass(String.valueOf(newValue), getActivity());
                updateUser();
                return true;
            }
        });
        edtEmail.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                User_Preferences.saveEmail(String.valueOf(newValue), getActivity());
                updateUser();
                return true;
            }
        });

    }

    private void updateUser() {
        RequestParams params = new RequestParams();
        params.put("email", User_Preferences.getEmail(getActivity()));
        params.put("password", User_Preferences.getPass(getActivity()));
        params.put("id", Repository.getInstance().getCurrentUser().getId());

        ApiClient.put(ApiClient.USERS, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Result result;
                Gson gson = new Gson();
                result = gson.fromJson(String.valueOf(response), Result.class);
                if (result != null) {
                    if (!result.getCode()) {
                        Log.e("Pref", result.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e("Pref", responseString);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e("Pref", throwable.getMessage());
            }
        });
    }
}
