package com.example.event_reminder.EventReminder;

import java.util.ArrayList;

public class helper {


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






}
