package com.example.eileen.hw7_6;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;



public class AlarmReceiver extends BroadcastReceiver {
    static int alarmCount = 0;

    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle bData = intent.getExtras();
        long timeElapsed = 0l;
        alarmCount++;
        if(bData != null)
            if(alarmCount==1)
                timeElapsed = intent.getLongExtra("time1", -1l)/1000;
            else{
                timeElapsed = intent.getLongExtra("time2", -2l)/1000;
            }

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(timeElapsed+ " seconds passed...")
                        .setDefaults(Notification.DEFAULT_SOUND);

        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(1, mBuilder.build());

    }
}

