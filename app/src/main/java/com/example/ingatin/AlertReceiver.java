package com.example.ingatin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import static com.example.ingatin.MainActivity.TAG;

public class AlertReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification(
                intent.getIntExtra("THE_ALARM_HOUR", 25) + ":" + intent.getStringExtra("THE_ALARM_MINUTE"),
                intent.getStringExtra("THE_ALARM_TITLE"));
        Log.d(TAG, "onReceive: " + intent.getIntExtra("THE_ALARM_HOUR", 25));
        notificationHelper.getManager().notify(1, nb.build());
    }
}
