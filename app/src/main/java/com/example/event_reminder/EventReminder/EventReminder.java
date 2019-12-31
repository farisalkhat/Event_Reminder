package com.example.event_reminder.EventReminder;


import android.app.PendingIntent;

import java.io.Serializable;
import java.time.*;
import java.util.Calendar;

public class EventReminder implements Serializable {
    String eventName;
    String eventDescription;
    Calendar eventCalendar;
    Calendar earlyBirdTime;

    String eventImportance;
    String eventCategory;
    PendingIntent pendingIntent;



    int notification_id;



    public EventReminder(String eventName, String eventDescription,
                         Calendar eventCalendar, Calendar earlyBirdTime,
                         String eventImportance, String eventCategory, int notification_id) {
        super();
        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.eventCalendar = eventCalendar;
        this.earlyBirdTime = earlyBirdTime;
        this.eventImportance = eventImportance;
        this.eventCategory = eventCategory;
        this.notification_id = notification_id;

    }


    public String getEventName(){
        return eventName;
    }
    public Calendar getEventDate(){
        return eventCalendar;
    }
    public Calendar getEarlyBirdTime(){
        return earlyBirdTime;
    }
    public int getNotification_id(){return notification_id;}
    public void setNotification_id(int notification_id){ this.notification_id = notification_id;}


    public String getEventDescription(){
        return eventDescription;
    }
    public String getEventImportance(){
        return eventImportance;
    }




}
