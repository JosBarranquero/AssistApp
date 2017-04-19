package com.bitbits.assistapp.fragments;

import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import com.bitbits.assistapp.AssistApp_Application;
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
                String password = String.valueOf(newValue);
                boolean save = false;
                if (TextUtils.isEmpty(password)) {      //If the password is empty
                    Toast.makeText(getActivity(), R.string.data_empty, Toast.LENGTH_SHORT).show();
                } else {
                    if (!(password.matches("(.*)\\d(.*)")))     //If the password doesn't contain a digit
                        Toast.makeText(getActivity(), R.string.password_digit, Toast.LENGTH_SHORT).show();
                    if (!(password.matches("(.*)\\p{Lower}(.*)") && password.matches("(.*)\\p{Upper}(.*)")))    //If the password doesn't contain at least a lowercase and uppercase letter
                        Toast.makeText(getActivity(), R.string.password_case, Toast.LENGTH_SHORT).show();
                    if (password.length() < 8)  //If it's less than 8 characters
                        Toast.makeText(getActivity(), R.string.password_length, Toast.LENGTH_SHORT).show();
                    else {      //If it's valid
                        save = updateUser(User_Preferences.getEmail(getActivity()), password);
                    }
                }
                return save;
            }
        });
        edtEmail.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                String email = String.valueOf(newValue);
                boolean save = false;
                if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {  //If it's a valid address
                    save = updateUser(email, User_Preferences.getPass(getActivity()));
                } else {                                                //If it's not a valid address
                    Toast.makeText(getActivity(), R.string.email_error, Toast.LENGTH_SHORT).show();
                }
                return save;
            }
        });

    }

    /**
     * Method which updates the user password and email in the database
     */
    private boolean updateUser(final String email, final String password) {
        RequestParams params = new RequestParams();
        params.put("email", email);
        params.put("password", password);
        params.put("id", Repository.getInstance().getCurrentUser().getId());

        final boolean[] updated = new boolean[1];

        ApiClient.put(ApiClient.USERS, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Result result;
                Gson gson = new Gson();
                result = gson.fromJson(String.valueOf(response), Result.class);
                if (result != null) {
                    if (result.getCode()) {
                        User_Preferences.savePass(password, getActivity());
                        User_Preferences.saveEmail(email, getActivity());
                        updated[0] = true;
                    } else {
                        Toast.makeText(AssistApp_Application.getContext(), result.getMessage(), Toast.LENGTH_SHORT).show();
                        updated[0] = false;
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(AssistApp_Application.getContext(), responseString, Toast.LENGTH_SHORT).show();
                Log.e("Pref", responseString);

                updated[0] = false;
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(AssistApp_Application.getContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("Pref", throwable.getMessage());

                updated[0] = false;
            }
        });
        return updated[0];
    }
}
