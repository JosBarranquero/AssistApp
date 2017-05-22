package com.bitbits.assistapp.receivers;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.NotificationCompat;

import com.bitbits.assistapp.AssistApp_Application;
import com.bitbits.assistapp.R;
import com.bitbits.assistapp.preferences.User_Preferences;

/**
 * Broadcast receiver which notifies the user if a new version is available
 * @author José Antonio Barranquero Fernández
 * @version 1.0
 *          AssistApp
 */
public class Version_Receiver extends BroadcastReceiver {
    public static final String ACTION_NEW_VERSION = "com.bitbits.assistapp.NEW_VERSION";

    @Override
    public void onReceive(Context context, Intent intent) {
        AssistApp_Application.stopMessageService();

        boolean sound = User_Preferences.getSound(context);
        boolean vibration = User_Preferences.getVibration(context);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context);
        notificationBuilder.setAutoCancel(true);

        notificationBuilder.setContentTitle(context.getString(R.string.app_name));
        notificationBuilder.setContentText(context.getString(R.string.old_version));

        if (sound)
            notificationBuilder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
        if (vibration)
            notificationBuilder.setVibrate(new long[]{500, 500});
        notificationBuilder.setLights(Color.GREEN, 3000, 3000);

        notificationBuilder.setSmallIcon(R.drawable.ic_notification);

        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(AssistApp_Application.URL + "apk/AssistApp.apk"));
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_ONE_SHOT);
        notificationBuilder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(2, notificationBuilder.build());
    }
}
