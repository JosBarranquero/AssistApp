package com.bitbits.assistapp.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;

import com.bitbits.assistapp.AssistApp_Application;
import com.bitbits.assistapp.Repository;
import com.bitbits.assistapp.models.Message;
import com.bitbits.assistapp.models.Result;
import com.bitbits.assistapp.preferences.User_Preferences;
import com.bitbits.assistapp.receivers.Message_Receiver;
import com.bitbits.assistapp.receivers.Version_Receiver;
import com.bitbits.assistapp.utilities.ApiClient;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


/**
 * Service which looks for new messages
 * @author José Antonio Barranquero Fernández
 * @version 1.1
 *          AssistApp
 */
public class Message_Service extends IntentService {
    private static final String TAG = "MsgSrv";

    private Repository mRepository = Repository.getInstance();

    public Message_Service() {
        super("Message_Service");
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        final Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (ApiClient.isNetworkAvailable()) {
                    Log.v(TAG, "Connecting...");

                    fetchNewMessages();
                } else {
                    Log.v(TAG, "No network");
                }
                handler.postDelayed(this, 10000);
            }
        }, 10000);

        return START_STICKY;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }

    /**
     * Method which fetches new messages
     */
    private void fetchNewMessages() {
        ApiClient.get(ApiClient.MESSAGES + "/" + User_Preferences.getId(AssistApp_Application.getContext()), null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Result result;
                Gson gson = new Gson();
                result = gson.fromJson(String.valueOf(response), Result.class);
                if (result != null) {
                    if (result.getCode()) {
                        if (!checkMessages(result.getMessages())) {
                            Log.v(TAG, "Got messages!");

                            mRepository.setUnread(result.getMessages());

                            Intent intent = new Intent();
                            Bundle bundle = new Bundle();
                            bundle.putInt(Message_Receiver.MESSAGE_COUNT, Repository.getInstance().getUnread().size());
                            intent.putExtras(bundle);
                            intent.setAction(Message_Receiver.ACTION_MESSAGE);
                            sendBroadcast(intent);
                        } else {
                            Log.v(TAG, "No new messages");
                        }
                    } else {
                        if (result.getStatus() == ApiClient.NEW_VERSION) {
                            if (AssistApp_Application.isServiceRunning()) {
                                Log.v(TAG, "Old version");
                                Intent intent = new Intent();
                                intent.setAction(Version_Receiver.ACTION_NEW);
                                sendBroadcast(intent);
                            }
                        }
                    }
                }
                Log.v(TAG, "Completed");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e(TAG, responseString);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e(TAG, throwable.getMessage());
            }
        });
    }

    /**
     * Method which checks if the returned messages are the same as the ones we got
     *
     * @param unread
     * @return
     */
    private boolean checkMessages(ArrayList<Message> unread) {
        boolean check = true;
        for (Message message : unread) {
            if (!mRepository.getUnread().contains(message)) {
                check = false;
                break;
            }
        }
        for (Message message : mRepository.getUnread()) {
            if (!unread.contains(message)) {
                check = false;
                break;
            }
        }
        return check;
    }
}
