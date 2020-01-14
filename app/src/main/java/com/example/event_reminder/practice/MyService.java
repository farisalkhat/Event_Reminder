package com.example.event_reminder.practice;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.Tag;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.event_reminder.MainActivity;

import static com.example.event_reminder.practice.App.CHANNEL_ID;

public class MyService extends Service
{
    @Override
    public void onCreate(){
        super.onCreate();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startID){
       String input = intent.getStringExtra("inputExtra");
       Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0,notificationIntent,0);

       Notification notification = new NotificationCompat.Builder(this,CHANNEL_ID)
               .setContentTitle("Example Service")
               .setContentText(input)
               .setSmallIcon(android.R.drawable.arrow_up_float)
               .setContentIntent(pendingIntent)
               .build();

       startForeground(1,notification);
       return START_REDELIVER_INTENT;


    }

    @Override
    public IBinder onBind(Intent arg0)
    {
        return null;
    }





}