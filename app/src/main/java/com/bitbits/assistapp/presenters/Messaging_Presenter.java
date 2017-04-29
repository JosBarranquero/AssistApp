package com.bitbits.assistapp.presenters;

import android.content.Context;
import android.util.Log;

import com.bitbits.assistapp.Repository;
import com.bitbits.assistapp.interfaces.IMessage;
import com.bitbits.assistapp.models.Message;
import com.bitbits.assistapp.models.Result;
import com.bitbits.assistapp.models.User;
import com.bitbits.assistapp.utilities.ApiClient;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import cz.msebera.android.httpclient.Header;

/**
 * Presenter for Messaging_Fragment
 *
 * @author José Antonio Barranquero Fernández
 * @version 1.0
 *          25/12/16
 */
public class Messaging_Presenter implements IMessage.Presenter/*, LoaderManager.LoaderCallbacks<Cursor>*/ {
    private IMessage.View mView;
    private Context context;
    private Repository mRepository = Repository.getInstance();

    public Messaging_Presenter(IMessage.View view) {
        mView = view;
        this.context = mView.getContext();
    }

    /**
     * Method which saves a message into the repository
     *
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
                        mRepository.writeMessage(message);
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

    /**
     * Method which sets the received messages from sender as read, removing them from out list
     * @param sender The user which sends the message
     */
    @Override
    public void readMessage(User sender) {
        ArrayList<Message> unread = mRepository.getUnread();
        Iterator<Message> iter = unread.iterator();

        while (iter.hasNext()) {
            Message message = iter.next();

            if (message.getSender() == sender.getId()) {
                iter.remove();
                mRepository.getUnread().remove(message);
            }
        }
    }

    public void getMessages(int receiver, int sender) {
        RequestParams params = new RequestParams();
        params.put("receiver", receiver);
        params.put("sender", sender);
        ApiClient.post(ApiClient.MESSAGES, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Result result;
                Gson gson = new Gson();
                result = gson.fromJson(String.valueOf(response), Result.class);
                if (result != null) {
                    if (result.getCode()) {
                        mRepository.setMessages(result.getMessages());

                        mView.setData();
                    } else {
                        Log.e("MSG", result.getMessage());
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
