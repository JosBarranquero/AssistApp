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
 * @author José Antonio Barranquero Fernández
 * @version 1.0
 *          AssistApp
 */

public class Message_Receiver extends BroadcastReceiver {
    public static final String ACTION_MESSAGE = "com.bitbits.assistapp.NEW_MESSAGE";
    public static final String MESSAGE_COUNT = "count";

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean sound = User_Preferences.getSound(context);
        boolean vibration = User_Preferences.getVibration(context);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context);
        notificationBuilder.setAutoCancel(true);

        int count = intent.getExtras().getInt(MESSAGE_COUNT);

        notificationBuilder.setContentTitle(context.getString(R.string.app_name));

        if (count > 1)
            notificationBuilder.setContentText(String.format(context.getString(R.string.new_messages), String.valueOf(count)));
        else
            notificationBuilder.setContentText(String.format(context.getString(R.string.new_message), String.valueOf(count)));

        if (sound)
            notificationBuilder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
        if (vibration)
            notificationBuilder.setVibrate(new long[]{500, 500});
        notificationBuilder.setLights(Color.GREEN, 3000, 3000);

        notificationBuilder.setSmallIcon(R.drawable.ic_new_message);

        Intent i = new Intent(context, Home_Activity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_ONE_SHOT);
        notificationBuilder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notificationBuilder.build());
    }
}
