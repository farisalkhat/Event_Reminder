package com.example.event_reminder;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.ViewGroup;

import androidx.core.app.NotificationCompat;

import com.example.event_reminder.EventReminder.EventReminder;
import com.example.event_reminder.EventReminder.Repeating_activity;
import com.example.event_reminder.EventReminder.helper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


public class Notification_receiver extends BroadcastReceiver {

    public static int activated_id;
    public static String title;



    @Override
    public void onReceive(Context context,Intent intent){
        //TODO: Implement receiver for important events as well.
        SharedPreferencesManager.loadStandardEventList(context);
        String title = EventReminderList.standardEventList.get(0).getEventName();
        String description = EventReminderList.standardEventList.get(0).getEventDescription();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent repeating_intent = new Intent(context,Repeating_activity.class);
        repeating_intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);




        try{
            repeating_intent.putExtra("NOTIFICATION_ID",1);
            PendingIntent pendingIntent = PendingIntent.getActivity(context,100,repeating_intent,PendingIntent.FLAG_UPDATE_CURRENT);


            NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(android.R.drawable.arrow_up_float)
                    .setContentTitle(EventReminderList.standardEventList.get(0).getEventName())
                    .setContentText(EventReminderList.standardEventList.get(0).getEventDescription())
                    .setAutoCancel(true);


            notificationManager.notify(EventReminderList.standardEventList.get(0).getNotification_id(),builder.build());

            EventReminder poopy = EventReminderList.standardEventList.get(0);
            EventReminderList.standardEventList.remove(0);
            EventReminderList.dumpEventList.add(poopy);

            SharedPreferencesManager.saveStandardEventList(context);
            SharedPreferencesManager.saveDumpEventList(context);
            
            EventReminderList.EventReminderList.invalidate();

        }catch (Exception e){
            Log.i("IndexOutOfBounds","There is nothing in standardEventList.");
        }


    }


}
