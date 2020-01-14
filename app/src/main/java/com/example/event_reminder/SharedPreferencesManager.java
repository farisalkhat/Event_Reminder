package com.example.event_reminder;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.event_reminder.EventReminder.EventReminder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SharedPreferencesManager {

    private SharedPreferencesManager() {}
    private static final String APP_SETTINGS = "shared preferences";

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(APP_SETTINGS, Context.MODE_PRIVATE);
    }

    public static void loadStandardEventList(Context context) {
        Gson gson = new Gson();
        String json = getSharedPreferences(context).getString("standardEventList", null);
        Type type = new TypeToken<ArrayList<EventReminder>>() {}.getType();

        EventReminderList.standardEventList = gson.fromJson(json, type);
        if(EventReminderList.standardEventList==null){
            EventReminderList.standardEventList = new ArrayList<EventReminder>();
            Log.i("standard","List was empty, so created new one");
        }
    }

    public static void loadDumpEventList(Context context) {
        Gson gson = new Gson();
        String json = getSharedPreferences(context).getString("dumpEventList", null);
        Type type = new TypeToken<ArrayList<EventReminder>>() {}.getType();

        EventReminderList.dumpEventList = gson.fromJson(json, type);
        if(EventReminderList.dumpEventList==null){
            EventReminderList.dumpEventList = new ArrayList<EventReminder>();
            Log.i("dump","List was empty, so created new one");
        }
    }

    public static void loadImportantEventList(Context context) {
        Gson gson = new Gson();
        String json = getSharedPreferences(context).getString("importantEventList", null);
        Type type = new TypeToken<ArrayList<EventReminder>>() {}.getType();

        EventReminderList.importantEventList = gson.fromJson(json, type);
        if(EventReminderList.importantEventList==null){
            EventReminderList.importantEventList = new ArrayList<EventReminder>();
            Log.i("important","List was empty, so created new one");
        }
    }


    public static void saveStandardEventList(Context context) {

        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        Gson gson = new Gson();
        String json = gson.toJson(EventReminderList.standardEventList);
        editor.putString("standardEventList",json);
        editor.commit();
        Log.i("saved","saved");
    }

    public static void saveDumpEventList(Context context) {

        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        Gson gson = new Gson();
        String json = gson.toJson(EventReminderList.dumpEventList);
        editor.putString("dumpEventList",json);
        editor.commit();
        Log.i("saved","saved");
    }

    public static void saveImportantEventList(Context context) {

        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        Gson gson = new Gson();
        String json = gson.toJson(EventReminderList.importantEventList);
        editor.putString("importantEventList",json);
        editor.commit();
        Log.i("saved","saved");
    }








}
