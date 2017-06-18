package com.bitbits.assistapp.presenters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.bitbits.assistapp.R;
import com.bitbits.assistapp.Repository;
import com.bitbits.assistapp.interfaces.IMessage;
import com.bitbits.assistapp.models.Message;
import com.bitbits.assistapp.models.Result;
import com.bitbits.assistapp.models.User;
import com.bitbits.assistapp.receivers.Message_Receiver;
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
 * @version 1.5
 *          25/12/16
 */
public class Messaging_Presenter implements IMessage.Presenter {
    private static final String TAG = "Msg";

    private IMessage.View mView;
    private Context mContext;
    private Repository mRepository = Repository.getInstance();

    private Handler mHandler = null;
    private Runnable mRunnable = null;

    public Messaging_Presenter(IMessage.View view) {
        mView = view;
        this.mContext = mView.getContext();
    }

    /**
     * Method which sends a message to another user
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

    /**
     * Method which sets the received messages from sender as read, removing them from out list
     *
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

        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putInt(Message_Receiver.MESSAGE_COUNT, mRepository.getUnread().size());
        bundle.putBoolean(Message_Receiver.NEW_NOTIFICATION, false);
        intent.putExtras(bundle);
        intent.setAction(Message_Receiver.ACTION_MESSAGE);
        mContext.sendBroadcast(intent);
    }

    /**
     * Method which gets the messages between receiver and sender, and keeps getting them every second to get an instant messaging-like service
     *
     * @param receiver
     * @param sender
     */
    public void getMessages(final int receiver, final int sender) {
        mRepository.setMessages(new ArrayList<Message>());  // Clearing local message list 'cache'

        mHandler = new Handler();

        mRunnable = new Runnable() {
            @Override
            public void run() {
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
                                if (result.getMessages().size() >= 1) { // If there are messages
                                    if (mRepository.getMessages().size() >= 1) {    // If  our local message list is not empty
                                        if (!mRepository.getMessages().get(mRepository.getMessages().size() - 1).equals(result.getMessages().get(result.getMessages().size() - 1))) {   // If the last message is different, we set the received messages
                                            mRepository.setMessages(result.getMessages());
                                            mView.setData();
                                        }
                                    } else { // If our local list is empty
                                        mRepository.setMessages(result.getMessages());
                                        mView.setData();
                                    }
                                } else {   // If there are no messages
                                    mRepository.setMessages(result.getMessages());
                                    mView.setData();
                                }
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
                mHandler.postDelayed(this, 1000);
            }
        };

        mHandler.post(mRunnable);
    }

    /**
     * Method which stops fetching messages between these users
     */
    @Override
    public void stopFetching() {
        mHandler.removeCallbacks(mRunnable);
    }
}
