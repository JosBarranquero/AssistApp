package com.bitbits.assistapp.receivers;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.provider.Settings;
import android.support.v7.app.NotificationCompat;

import com.bitbits.assistapp.Home_Activity;
import com.bitbits.assistapp.R;
import com.bitbits.assistapp.preferences.User_Preferences;

/**
 * Receiver which notifies the user when a message is received
 *
 * @author José Antonio Barranquero Fernández
 * @version 1.0
 *          AssistApp
 */
public class Message_Receiver extends BroadcastReceiver {
    public static final String ACTION_MESSAGE = "com.bitbits.assistapp.NEW_MESSAGE";
    public static final String MESSAGE_COUNT = "count";
    public static final String NEW_NOTIFICATION = "notification";

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean sound = User_Preferences.getSound(context);
        boolean vibration = User_Preferences.getVibration(context);
        int count = intent.getExtras().getInt(MESSAGE_COUNT);
        boolean newNotification = intent.getExtras().getBoolean(NEW_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context);
        notificationBuilder.setAutoCancel(true);

        notificationBuilder.setContentTitle(context.getString(R.string.app_name));


        if (count > 1)
            notificationBuilder.setContentText(String.format(context.getString(R.string.new_messages), String.valueOf(count)));
        else
            notificationBuilder.setContentText(String.format(context.getString(R.string.new_message), String.valueOf(count)));

        if (newNotification) {  // If it's a new notification, the device may vibrate and make a sound
            if (sound)
                notificationBuilder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
            if (vibration)
                notificationBuilder.setVibrate(new long[]{250, 250});
        }
        notificationBuilder.setLights(Color.GREEN, 1000, 1000);

        notificationBuilder.setSmallIcon(R.drawable.ic_notification);

        Intent i = new Intent(context, Home_Activity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_ONE_SHOT);
        notificationBuilder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (count == 0) {       // If the message count is 0, we delete the notification
            notificationManager.cancel(1);
        } else {
            notificationManager.notify(1, notificationBuilder.build());
        }
    }
}
