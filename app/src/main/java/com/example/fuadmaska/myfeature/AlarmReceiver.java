package com.example.fuadmaska.myfeature;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {
//    MediaPlayer player;

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent notificationInten =new Intent(context,MainActivity.class);
        notificationInten.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(context);
        taskStackBuilder.addParentStack(MainActivity.class) ;
        taskStackBuilder.addNextIntent(notificationInten);

        PendingIntent pendingIntent=taskStackBuilder.getPendingIntent(100, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder nBuilder = new  NotificationCompat.Builder(context) ;
        Notification notification = nBuilder.setContentTitle("Alarm Bayar Premi")
                .setContentText("Bangun Cuy")
                .setSound(alarmSound)
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher).setContentIntent(pendingIntent).build() ;
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE) ;
        notificationManager.notify(0, notification);
        Toast.makeText(context, "Alarm aktif!", Toast.LENGTH_LONG).show();
//        player = MediaPlayer.create(context, R.raw.alarm);
//        player.start();
    }
}
