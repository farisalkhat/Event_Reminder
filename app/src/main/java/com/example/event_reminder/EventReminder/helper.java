package com.example.event_reminder.EventReminder;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.event_reminder.EventReminderList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class helper {
    static Type type = new TypeToken<ArrayList<EventReminder>>() {}.getType();


    public static void sortEvents(ArrayList<EventReminder> eventList){

        for(int i = 0; i<eventList.size();i++){
            if(i+1<eventList.size()){
                if (eventList.get(i).getEventDate().getTime().after(eventList.get(i+1).getEventDate().getTime())){
                    EventReminder temp = eventList.get(i);
                    eventList.set(i,eventList.get(i+1));
                    eventList.set(i+1,temp);
                }
            }
        }

    }

    public static EventReminder searchEvents(ArrayList<EventReminder> eventList, int notification_id) {
        for (int i = 0; i < eventList.size(); i++) {
            if (eventList.get(i).getNotification_id() == notification_id) {
                return eventList.get(i);
            }
        }
        return null;
    }

    public static int searchEventPlacement(ArrayList<EventReminder> eventList, int notification_id) {
        Log.i("Searching for:",""+notification_id);
        for (int i = 0; i < eventList.size(); i++) {
            Log.i("EventReminder ID:","" + eventList.get(i).getNotification_id());
            if (eventList.get(i).getNotification_id() == notification_id) {
                return i;
            }
        }
        return -1;
    }

    public static void saveStandardEvents(SharedPreferences mPrefs){
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(EventReminderList.standardEventList);
        prefsEditor.putString("standardEventList",json);
        prefsEditor.commit();
        Log.i("saved","saved");
    }
    public static void loadStandardEvents(SharedPreferences mPrefs){
        Gson gson = new Gson();
        String json = mPrefs.getString("standardEventList", null);

        EventReminderList.standardEventList = gson.fromJson(json, type);
        if(EventReminderList.standardEventList==null){
            EventReminderList.standardEventList = new ArrayList<EventReminder>();
            Log.i("standard","List was empty, so created new one");
        }
    }


    public static void saveDumpEvents(SharedPreferences mPrefs){
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(EventReminderList.dumpEventList);
        prefsEditor.putString("dumpEventList",json);
        prefsEditor.commit();
    }
    public static void saveImportantEvents(SharedPreferences mPrefs){
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(EventReminderList.importantEventList);
        prefsEditor.putString("importantEventList",json);
        prefsEditor.commit();
    }

    public static void loadDumpEvents(SharedPreferences mPrefs) throws IOException {
        Gson gson = new Gson();
        String json = mPrefs.getString("dumpEventList", null);
        EventReminderList.dumpEventList = gson.fromJson(json, type);
        if(EventReminderList.dumpEventList==null){
            EventReminderList.dumpEventList = new ArrayList<EventReminder>();
        }
    }
    public static void loadImportantEvents(SharedPreferences mPrefs) throws IOException {
        Gson gson = new Gson();
        String json = mPrefs.getString("importantEventList", null);
        EventReminderList.importantEventList = gson.fromJson(json, type);
        if(EventReminderList.importantEventList==null){
            EventReminderList.importantEventList = new ArrayList<EventReminder>();
        }

    }






}
