package com.csetlu.dontforgettodoit;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        String channelId = "todonotify";
        Bundle bundle = intent.getExtras();
        int maCV = bundle.getInt("maCV");
        String congViec = bundle.getString("congViec");
        String ngayGio = bundle.getString("ngay") + " " + bundle.getString("thoiGian");

        PendingIntent pendingIntent = PendingIntent.getActivity(context, maCV, intent, PendingIntent.FLAG_ONE_SHOT);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder notify = new NotificationCompat.Builder(context, channelId);

        notify.setContentTitle(congViec);
        notify.setContentText(ngayGio);
        notify.setSmallIcon(R.drawable.notification);
        notify.setAutoCancel(true);
        notify.setOngoing(true);
        notify.setPriority(Notification.PRIORITY_HIGH);
        notify.build().flags = Notification.FLAG_NO_CLEAR | Notification.PRIORITY_HIGH;
        notify.setContentIntent(pendingIntent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "notify", NotificationManager.IMPORTANCE_HIGH);
            channel.enableVibration(true);
            notificationManager.createNotificationChannel(channel);
            notify.setChannelId(channelId);
        }
        Notification notification = notify.build();
        notificationManager.notify(maCV, notification);
    }
}

