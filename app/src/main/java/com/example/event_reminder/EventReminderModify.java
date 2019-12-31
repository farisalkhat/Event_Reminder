package com.example.event_reminder;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.event_reminder.EventReminder.EventReminder;
import com.example.event_reminder.EventReminder.helper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Calendar;

public class EventReminderModify extends AppCompatActivity {

    EditText modifyEventTitle;
    EditText modifyEventDesc;
    ToggleButton modifyEventImp;
    Button modifyEventTime;
    Button modifyEventEarly;
    EventReminder modifyEvent;

    Calendar date;
    Calendar earlyDate;
    boolean newDate = false;
    boolean newEarlyDate = false;
    EventReminder Reminder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        modifyEvent = EventReminderList.selectedEvent;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_reminder_modify);

        modifyEventTitle = findViewById(R.id.modifyEventTitle);
        modifyEventDesc = findViewById(R.id.modifyEventDesc);
        modifyEventImp = findViewById(R.id.modifyEventImp);
        modifyEventTime = findViewById(R.id.modifyEventTime);
        modifyEventEarly = findViewById(R.id.modifyEventEarly);

        modifyEventTitle.setText(modifyEvent.getEventName());
        modifyEventDesc.setText(modifyEvent.getEventDescription());


    }


    public void showModifyDateTimePicker(View view) {

        newDate = false;
        final Calendar currentDate = Calendar.getInstance();
        date = Calendar.getInstance();
        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date.set(year, monthOfYear, dayOfMonth);
                if(date.getTime().before(currentDate.getTime())){
                    Toast.makeText(EventReminderModify.this,"Time is not possible, try again.",Toast.LENGTH_SHORT).show();
                }
                else{
                    new TimePickerDialog(EventReminderModify.this, new TimePickerDialog.OnTimeSetListener() {
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

    public void showModifyEarlyDateTimePicker(View view) {
        newEarlyDate = false;
        final Calendar currentDate = Calendar.getInstance();
        earlyDate = Calendar.getInstance();
        new DatePickerDialog(EventReminderModify.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                earlyDate.set(year, monthOfYear, dayOfMonth);
                if(earlyDate.getTime().before(currentDate.getTime())){
                    Toast.makeText(EventReminderModify.this,"Time is not possible, try again.",Toast.LENGTH_SHORT).show();
                    earlyDate = date;
                }
                else{
                    new TimePickerDialog(EventReminderModify.this, new TimePickerDialog.OnTimeSetListener() {
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


    public void modifyEvent(View view){


        if (newDate == false){
            Toast.makeText(EventReminderModify.this,"Add a time!",Toast.LENGTH_SHORT).show();
        }

        else if(modifyEventTitle.getText().toString().equals("")){
            Toast.makeText(EventReminderModify.this,"Add a title!",Toast.LENGTH_SHORT).show();
        }

        else{

            if (modifyEventImp.isChecked()){
                Reminder = new EventReminder(modifyEventTitle.getText().toString(),modifyEventTitle.getText().toString(),
                        date,earlyDate,"IMPORTANT","CURRENT",modifyEvent.getNotification_id());
                int reminderPlacement = helper.searchEventPlacement(EventReminderList.importantEventList,modifyEvent.getNotification_id());
                EventReminderList.importantEventList.remove(reminderPlacement);
                EventReminderList.importantEventList.add(Reminder);
                helper.sortEvents(EventReminderList.importantEventList);
                try {
                    Context context = getApplicationContext();
                    helper.saveImportantEvents(context);
                }
                catch(IOException ex){
                    System.out.println("IOException is caught: Failure to save replays");
                }

            }
            else{
                Reminder = new EventReminder(modifyEventTitle.getText().toString(),modifyEventDesc.getText().toString(),
                        date,earlyDate,"STANDARD","CURRENT",modifyEvent.getNotification_id());
                int reminderPlacement = helper.searchEventPlacement(EventReminderList.standardEventList,modifyEvent.getNotification_id());
                EventReminderList.standardEventList.remove(reminderPlacement);
                EventReminderList.standardEventList.add(Reminder);
                helper.sortEvents(EventReminderList.standardEventList);
                try {
                    Context context = getApplicationContext();
                    helper.saveStandardEvents(context);
                }
                catch(IOException ex){
                    System.out.println("IOException is caught: Failure to save replays");
                }

            }

            //Remove old reminder
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent oldIntent  = new Intent(getApplicationContext(),Notification_receiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),modifyEvent.getNotification_id(),oldIntent,PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.cancel(pendingIntent);

            //Add new reminder
            Intent intent  = new Intent(getApplicationContext(),Notification_receiver.class);
            intent.putExtra("NOTIFICATION_ID",modifyEvent.getNotification_id());
            pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),modifyEvent.getNotification_id(),intent,PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.set(AlarmManager.RTC_WAKEUP,date.getTimeInMillis(),pendingIntent);

            //Sort list after modifying it.
            Intent poop = new Intent(this, EventReminderList.class);
            EventDetails.modifyEvent = false;
            Reminder = null;
            startActivity(poop);
        }

    }



    public void cancelModifyEvent(View view){
        EventDetails.modifyEvent=false;
        Intent intent = new Intent(this, EventDetails.class);
        startActivity(intent);
    }






}
