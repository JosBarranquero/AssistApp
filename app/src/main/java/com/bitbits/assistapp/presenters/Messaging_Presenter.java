package com.bitbits.assistapp.presenters;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.CursorAdapter;

import com.bitbits.assistapp.Repository;
import com.bitbits.assistapp.database.DatabaseContract;
import com.bitbits.assistapp.database.DatabaseHelper;
import com.bitbits.assistapp.interfaces.IMessage;
import com.bitbits.assistapp.models.Message;
import com.bitbits.assistapp.models.Result;
import com.bitbits.assistapp.provider.ProviderContract;
import com.bitbits.assistapp.utilities.ApiClient;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Presenter for Messaging_Fragment
 * @author José Antonio Barranquero Fernández
 * @version 1.0
 *          25/12/16
 */
public class Messaging_Presenter implements IMessage.Presenter/*, LoaderManager.LoaderCallbacks<Cursor>*/ {
    public static final int MESSAGES = 1;

    IMessage.View mView;
    Context context;

    public Messaging_Presenter(IMessage.View view) {
        mView = view;
        this.context = mView.getContext();
    }

    /**
     * Method which saves a message into the repository
     * @param message
     * @see Message
     */
    public void sendMessage(final Message message) {
        RequestParams params = new RequestParams();
        params.put("content", message.getContent());
        params.put("sender", message.getSender());
        params.put("receiver", message.getReceiver());
        ApiClient.put(ApiClient.MESSAGES, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Result result;
                Gson gson = new Gson();
                result = gson.fromJson(String.valueOf(response), Result.class);
                if (result != null) {
                    if (!result.getCode()) {
                        Log.e("MSG", result.getMessage());
                    } else {
                        Repository.getInstance().writeMessage(message);
                        mView.message();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e("MSG", responseString);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e("MSG", throwable.getMessage());
            }
        });
    }
}
