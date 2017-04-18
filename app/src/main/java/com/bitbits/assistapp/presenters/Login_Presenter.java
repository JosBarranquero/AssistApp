package com.bitbits.assistapp.presenters;

import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.bitbits.assistapp.R;
import com.bitbits.assistapp.Repository;
import com.bitbits.assistapp.interfaces.IAccount;
import com.bitbits.assistapp.models.Result;
import com.bitbits.assistapp.preferences.User_Preferences;
import com.bitbits.assistapp.utilities.ApiClient;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Presenter for Login_Activity
 *
 * @author José Antonio Barranquero Fernández
 * @version 1.0
 */
public class Login_Presenter implements IAccount.Presenter {
    Repository data = Repository.getInstance();
    IAccount.View view;
    Context context;

    public Login_Presenter(IAccount.View view) {
        this.view = view;
        context = view.getContext();
    }

    /**
     * Method which validates if the username entered is valid
     *
     * @param user The username to be validated
     * @return True - if it's valid
     * False - if it's not valid
     */
    @Override
    public boolean validateUser(String user) {
        boolean valid = false;
        if (TextUtils.isEmpty(user)) {
            view.setErrorMessage(((Context) view).getResources().getString(R.string.data_empty), R.id.edtUser);
        } else {
            valid = true;
        }
        return valid;
    }

    /**
     * Method which validates if the password entered is valid
     *
     * @param password The password to be validated
     * @return True - if it's valid
     * False - if it's not valid
     */
    @Override
    public boolean validatePassword(String password) {
        String error = "";
        boolean valid = false;
        if (TextUtils.isEmpty(password)) {
            error = ((Context) view).getResources().getString(R.string.data_empty);
        } else {
            if (!(password.matches("(.*)\\d(.*)")))
                error = ((Context) view).getResources().getString(R.string.password_digit);
            if (!(password.matches("(.*)\\p{Lower}(.*)") && password.matches("(.*)\\p{Upper}(.*)")))
                error = ((Context) view).getResources().getString(R.string.password_case);
            if (password.length() < 8)
                error = ((Context) view).getResources().getString(R.string.password_length);
            else
                valid = true;
        }
        if (!valid)
            view.setErrorMessage(error, R.id.edtPassword);
        return valid;
    }

    /**
     * Method which validates the login credentials and check if they exists.
     * If they do, it launches the next activity
     *
     * @param user     The username
     * @param password The password
     */
    public void validateCredentials(final String user, final String password) {
        if (validateUser(user) && validatePassword(password)) {
            final ProgressDialog progressDialog = new ProgressDialog(view.getContext());

            RequestParams params = new RequestParams();
            params.put("idDoc", user);
            params.put("password", password);

            ApiClient.post(ApiClient.USERS, params, new JsonHttpResponseHandler() {
                @Override
                public void onStart() {
                    super.onStart();
                    if (User_Preferences.getPass(context) == null && User_Preferences.getUser(context) == null) {
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        progressDialog.setMessage(view.getContext().getString(R.string.loggingin));
                        progressDialog.setCancelable(false);
                        progressDialog.show();
                    }
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject responseBody) {
                    progressDialog.dismiss();
                    Result result;
                    Gson gson = new Gson();
                    result = gson.fromJson(String.valueOf(responseBody), Result.class);
                    if (result != null) {
                        if (result.getCode()) {
                            data.setCurrentUser(result.getUsers().get(0));
                            data.getCurrentUser().setPassword(password);
                            if (User_Preferences.getPass(context) == null && User_Preferences.getUser(context) == null) {
                                User_Preferences.saveUser(data.getCurrentUser().getIdDoc(), context);
                                User_Preferences.savePass(data.getCurrentUser().getPassword(), context);
                                User_Preferences.saveEmail(data.getCurrentUser().getEmail(), context);
                            }
                            view.launchActivity();
                        } else {
                            view.setErrorMessage(context.getString(R.string.credentials_error), R.id.edtPassword);
                        }
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    progressDialog.dismiss();
                    Log.e("Login", responseString);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    progressDialog.dismiss();
                    Log.e("Login", throwable.getMessage());
                }
            });
        }
    }
}
