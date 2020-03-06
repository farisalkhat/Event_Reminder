package com.example.event_reminder;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;


import com.example.event_reminder.EventReminder.EventReminder;
import com.example.event_reminder.EventReminder.EventCategory;
import com.example.event_reminder.EventReminder.EventImportance;
import com.example.event_reminder.EventReminder.helper;
import com.google.gson.Gson;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.w3c.dom.Text;

public class eventreminder_add extends AppCompatActivity {
    Calendar date;
    Calendar earlyDate;
    boolean newDate = false;
    boolean newEarlyDate = false;
    public static EventReminder Reminder;
    public static int notification_id = 1; // This is the id for each notifcation. It will go up by one every time a new notification is made.



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventreminder_add);
    }

    public void showDateTimePicker(View view) {

        newDate = false;
        final Calendar currentDate = Calendar.getInstance();
        date = Calendar.getInstance();
        new DatePickerDialog(eventreminder_add.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date.set(year, monthOfYear, dayOfMonth);
                if(date.getTime().before(currentDate.getTime())){
                    Toast.makeText(eventreminder_add.this,"Time is not possible, try again.",Toast.LENGTH_SHORT).show();
                }
                else{
                    new TimePickerDialog(eventreminder_add.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            date.set(Calendar.HOUR_OF_DAY, hourOfDay);
                            date.set(Calendar.MINUTE, minute);
                            Log.v("Info", "The choosen one " + date.getTime());
                            newDate = true;
                            if(newEarlyDate==false){
                                earlyDate = date;
                            }
                        }
                    }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), false).show();
                }

            }
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();
    }

    public void showEarlyDateTimePicker(View view) {
        newEarlyDate = false;
        final Calendar currentDate = Calendar.getInstance();
        earlyDate = Calendar.getInstance();
        new DatePickerDialog(eventreminder_add.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                earlyDate.set(year, monthOfYear, dayOfMonth);
                if(earlyDate.getTime().before(currentDate.getTime())){
                    Toast.makeText(eventreminder_add.this,"Time is not possible, try again.",Toast.LENGTH_SHORT).show();
                    earlyDate = date;
                }
                else{
                    new TimePickerDialog(eventreminder_add.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            earlyDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
                            earlyDate.set(Calendar.MINUTE, minute);
                            Log.v("Info", "The choosen one " + earlyDate.getTime());
                            newEarlyDate = true;
                        }
                    }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), false).show();
                }
            }
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();
    }

    public void createEvent(View view){
        TextView eventTitle = (TextView)findViewById(R.id.eventTitle);
        ToggleButton importantToggle = (ToggleButton)findViewById(R.id.importantToggle);
        TextView eventDescription = (TextView)findViewById(R.id.eventDescription);

        if (newDate == false){
            Toast.makeText(eventreminder_add.this,"Add a time!",Toast.LENGTH_SHORT).show();
        }

        else if(eventTitle.getText().toString().equals("")){
            Toast.makeText(eventreminder_add.this,"Add a title!",Toast.LENGTH_SHORT).show();
        }

        else{

            if (importantToggle.isChecked()){
                Reminder = new EventReminder(eventTitle.getText().toString(),eventDescription.getText().toString(),
                        date,earlyDate,"IMPORTANT","CURRENT",notification_id);
                EventReminderList.importantEventList.add(Reminder);
                helper.sortEvents(EventReminderList.importantEventList);
                SharedPreferencesManager.saveImportantEventList(getApplicationContext());

            }
            else{
                Reminder = new EventReminder(eventTitle.getText().toString(),eventDescription.getText().toString(),
                        date,earlyDate,"STANDARD","CURRENT",notification_id);
                EventReminderList.standardEventList.add(Reminder);
                helper.sortEvents(EventReminderList.standardEventList);
                SharedPreferencesManager.saveStandardEventList(getApplicationContext());

            }





            Intent intent  = new Intent(getApplicationContext(),Notification_receiver.class);
            //Intent intent = new Intent(this, ExampleIntentService.class);

            intent.putExtra("NOTIFICATION_ID",notification_id);
            //This intent is attached to our notification_receiver class, which will create the notification when the intent is called.
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),notification_id,intent,PendingIntent.FLAG_UPDATE_CURRENT);
            notification_id++;
            //To use alarmmanager/notifications, it's required to use a pendingintent, as this is needed to gain permission to use alarmmanager within our application.
            //We simply take our intent and make it into a pending intent
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            //We setup the alarmmanager here.
            alarmManager.set(AlarmManager.RTC_WAKEUP,date.getTimeInMillis(),pendingIntent);
            //Lastly, we set what time we want the alarm to be



            Intent poop = new Intent(this, EventReminderList.class);
            startActivity(poop);
        }

    }

    public void cancelEvent(View view){
        Intent intent = new Intent(this, EventReminderList.class);
        startActivity(intent);
    }



}



