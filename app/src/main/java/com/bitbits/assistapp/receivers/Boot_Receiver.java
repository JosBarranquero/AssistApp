package com.bitbits.assistapp.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.bitbits.assistapp.AssistApp_Application;

/**
 * Receiver which starts the Message_Service when the phone has finished booting up
 * @author José Antonio Barranquero Fernández
 * @version 1.0
 *          AssistApp
 */
public class Boot_Receiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        AssistApp_Application.startMessageService();
    }
}