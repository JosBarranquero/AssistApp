package com.bitbits.assistapp.presenters;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.util.Patterns;

import com.bitbits.assistapp.R;
import com.bitbits.assistapp.interfaces.IPassword;
import com.bitbits.assistapp.models.Result;
import com.bitbits.assistapp.utilities.ApiClient;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Presenter for Forgot_Activity
 *
 * @author José Antonio Barranquero Fernández
 * @version 1.0
 *          AssistApp
 */

public class Forgot_Presenter implements IPassword.Presenter {
    private static final String TAG = "Forgot";

    private IPassword.View mView;
    private Context context;

    public Forgot_Presenter(IPassword.View mView) {
        this.mView = mView;
        this.context = mView.getContext();
    }

    /**
     * Method which connects with the API to reset the password, if the email and idDoc are a user's
     *
     * @param email User email
     * @param idDoc User idDoc
     */
    @Override
    public void resetPassword(String email, String idDoc) {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
            mView.showMessage(context.getString(R.string.email_error));
        else {
            final ProgressDialog progressDialog = new ProgressDialog(context);

            RequestParams params = new RequestParams();
            params.put("email", email);
            params.put("idDoc", idDoc);

            ApiClient.post(ApiClient.RESET, params, new JsonHttpResponseHandler() {
                @Override
                public void onStart() {
                    super.onStart();
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.setMessage(context.getString(R.string.connecting));
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    progressDialog.dismiss();
                    Result result;
                    Gson gson = new Gson();
                    result = gson.fromJson(String.valueOf(response), Result.class);
                    if (result != null) {
                        if (result.getCode()) {
                            mView.showMessage(context.getString(R.string.successful_reset));
                        } else {
                            if (result.getStatus() == ApiClient.WRONG_CREDENTIALS)
                                mView.showMessage(context.getString(R.string.failure_reset));
                            else
                                mView.showMessage(context.getString(R.string.connection_error));
                        }
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    progressDialog.dismiss();
                    mView.showMessage(context.getString(R.string.connection_error));
                    Log.e(TAG, responseString);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    progressDialog.dismiss();
                    mView.showMessage(context.getString(R.string.connection_error));
                    Log.e(TAG, throwable.getMessage());
                }
            });
        }
    }
}
