package com.example.event_reminder.EventReminder;

import android.content.Context;
import android.util.Log;

import com.example.event_reminder.EventReminderList;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

    public static void saveStandardEvents(Context context) throws IOException{
        File f = new File(".");
        FileOutputStream fos = context.openFileOutput("savedStandardEvents.dat",Context.MODE_PRIVATE);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(EventReminderList.standardEventList);
        oos.close();
        fos.close();
    }
    public static void saveDumpEvents(Context context) throws IOException{
        File f = new File(".");
        FileOutputStream fos = context.openFileOutput("savedDumpEvents.dat",Context.MODE_PRIVATE);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(EventReminderList.dumpEventList);
        oos.close();
        fos.close();
    }
    public static void saveImportantEvents(Context context) throws IOException{
        File f = new File(".");
        FileOutputStream fos = context.openFileOutput("savedImportantEvents.dat",Context.MODE_PRIVATE);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(EventReminderList.importantEventList);
        oos.close();
        fos.close();
    }
    public static void loadStandardEvents(Context context) throws IOException, ClassNotFoundException {
        FileInputStream fis = context.openFileInput("savedStandardEvents.dat");
        ObjectInputStream ois = new ObjectInputStream(fis);
        EventReminderList.standardEventList = (ArrayList)ois.readObject();
        ois.close();
        fis.close();
    }
    public static void loadDumpEvents(Context context) throws IOException, ClassNotFoundException {
        FileInputStream fis = context.openFileInput("savedDumpEvents.dat");
        ObjectInputStream ois = new ObjectInputStream(fis);
        EventReminderList.dumpEventList = (ArrayList)ois.readObject();
        ois.close();
        fis.close();
    }
    public static void loadImportantEvents(Context context) throws IOException, ClassNotFoundException {
        FileInputStream fis = context.openFileInput("savedImportantEvents.dat");
        ObjectInputStream ois = new ObjectInputStream(fis);
        EventReminderList.importantEventList = (ArrayList)ois.readObject();
        ois.close();
        fis.close();
    }


    /**
     fis = context.openFileInput("savedDumpedEvents.dat");
     ois = new ObjectInputStream(fis);
     EventReminderList.dumpEventList = (ArrayList)ois.readObject();

     fis = context.openFileInput("savedImportantEvents.dat");
     ois = new ObjectInputStream(fis);
     EventReminderList.importantEventList = (ArrayList)ois.readObject();
     **/



}
