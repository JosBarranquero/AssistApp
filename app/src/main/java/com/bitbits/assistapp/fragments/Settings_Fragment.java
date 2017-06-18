package com.bitbits.assistapp.fragments;

import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;

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
 *
 * @author José Antonio Barranquero Fernández
 * @version 1.0
 */
public class Settings_Fragment extends PreferenceFragment {
    private static final String TAG = "Settings";

    private EditTextPreference mEdtPassword;
    private EditTextPreference mEdtEmail;

    private Repository mRepository = Repository.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings_app);

        setRetainInstance(true);

        mEdtPassword = (EditTextPreference) findPreference("password");
        mEdtEmail = (EditTextPreference) findPreference("email");

        mEdtPassword.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                String password = String.valueOf(newValue);
                boolean save = false;
                if (TextUtils.isEmpty(password)) {      //If the password is empty
                    if (getView() != null)
                        Snackbar.make(getView(), R.string.data_empty, Snackbar.LENGTH_SHORT).show();
                } else {
                    if (!(password.matches("(.*)\\d(.*)"))) {    //If the password doesn't contain a digit
                        if (getView() != null)
                            Snackbar.make(getView(), R.string.password_digit, Snackbar.LENGTH_SHORT).show();
                    } else {
                        if (!(password.matches("(.*)\\p{Lower}(.*)") && password.matches("(.*)\\p{Upper}(.*)"))) {  //If the password doesn't contain at least a lowercase and uppercase letter
                            if (getView() != null)
                                Snackbar.make(getView(), R.string.password_case, Snackbar.LENGTH_SHORT).show();
                        } else if (password.length() < 8) { //If it's less than 8 characters
                            if (getView() != null)
                                Snackbar.make(getView(), R.string.password_length, Snackbar.LENGTH_SHORT).show();
                        } else {      //If it's valid
                            save = updateUser(User_Preferences.getEmail(getActivity()), password);
                            mRepository.getCurrentUser().setDefaultPass(0);
                        }
                    }
                }
                return save;
            }
        });
        mEdtEmail.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                String email = String.valueOf(newValue);
                boolean save = false;
                if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {  //If it's a valid address
                    save = updateUser(email, User_Preferences.getPass(getActivity()));
                } else {                                                //If it's not a valid address
                    if (getView() != null)
                        Snackbar.make(getView(), R.string.email_error, Snackbar.LENGTH_SHORT).show();
                }
                return save;
            }
        });

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (mRepository.getCurrentUser().passwordIsRestored()) {
            if (getView() != null)
                Snackbar.make(getView(), R.string.change_password, Snackbar.LENGTH_LONG).show();
        }
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
                        if (getView() != null)
                            Snackbar.make(getView(), result.getMessage(), Snackbar.LENGTH_SHORT).show();

                        Log.e(TAG, result.getMessage());
                        updated[0] = false;
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                if (getView() != null)
                    Snackbar.make(getView(), responseString, Snackbar.LENGTH_LONG).show();

                Log.e(TAG, responseString);
                updated[0] = false;
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                if (getView() != null)
                    Snackbar.make(getView(), throwable.getMessage(), Snackbar.LENGTH_SHORT).show();

                Log.e(TAG, throwable.getMessage());
                updated[0] = false;
            }
        });
        return updated[0];
    }
}
