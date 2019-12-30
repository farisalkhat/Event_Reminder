package com.example.event_reminder.EventReminder;


import java.time.*;
import java.util.Calendar;

public class EventReminder {
    String eventName;
    String eventDescription;
    Calendar eventCalendar;
    Calendar earlyBirdTime;

    String eventImportance;
    String eventCategory;

    EventReminder next;
    EventReminder prev;

    int notification_id;



    public EventReminder(String eventName, String eventDescription,
                         Calendar eventCalendar, Calendar earlyBirdTime,
                         String eventImportance, String eventCategory, int notification_id) {

        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.eventCalendar = eventCalendar;
        this.earlyBirdTime = earlyBirdTime;
        this.eventImportance = eventImportance;
        this.eventCategory = eventCategory;
        this.notification_id = notification_id;

        this.next = null;
        this.prev = null;



    }

    public EventReminder getNext(){return next;}
    public void setNext(EventReminder next){this.next = next;}
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
