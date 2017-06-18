package com.bitbits.assistapp.presenters;

import android.content.Context;
import android.util.Log;

import com.bitbits.assistapp.R;
import com.bitbits.assistapp.Repository;
import com.bitbits.assistapp.interfaces.IConversation;
import com.bitbits.assistapp.models.Result;
import com.bitbits.assistapp.utilities.ApiClient;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Presenter for ConversationList_Fragment
 *
 * @author José Antonio Barranquero Fernández
 * @version 1.0
 *          AssistApp
 */
public class ConversationList_Presenter implements IConversation.Presenter {
    private static final String TAG = "Assist";

    private IConversation.View mView;
    private Context mContext;

    public ConversationList_Presenter(IConversation.View view) {
        this.mView = view;
        this.mContext = view.getContext();
    }

    @Override
    public void getUsers() {
        ApiClient.get(ApiClient.ASSISTS + "/" + Repository.getInstance().getCurrentUser().getId(), new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Result result;
                Gson gson = new Gson();
                result = gson.fromJson(String.valueOf(response), Result.class);
                if (result != null) {
                    if (result.getCode()) {
                        Repository.getInstance().setUsers(result.getUsers());

                        mView.setData();
                    } else {
                        Log.e(TAG, result.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e(TAG, responseString);
                mView.showMessage(mContext.getString(R.string.connection_error));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e(TAG, throwable.getMessage());
                mView.showMessage(mContext.getString(R.string.connection_error));
            }
        });
    }
}
