package com.example.event_reminder;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.event_reminder.EventReminder.EventReminder;
import com.example.event_reminder.EventReminder.helper;

public class EventDetails extends AppCompatActivity {


    TextView selectedEventDesc;
    TextView selectedEventName;
    TextView selectedEventDate;
    TextView selectedEventImp;
    int reminder ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        selectedEventName = findViewById(R.id.titleEventName);
        selectedEventDesc = findViewById(R.id.selectedEventDesc);
        selectedEventDate = findViewById(R.id.selectedEventDate);
        selectedEventImp = findViewById(R.id.selectedEventImp);

        selectedEventName.setText(EventReminderList.selectedEvent.getEventName());
        selectedEventDesc.setText(EventReminderList.selectedEvent.getEventDescription());
        selectedEventDate.setText(EventReminderList.selectedEvent.getEventDate().getTime().toString());
        selectedEventImp.setText(EventReminderList.selectedEvent.getEventImportance());


    }

    public void cancelCurrentEvent(View view) {
        Intent intent = new Intent(this, EventReminderList.class);
        startActivity(intent);
    }
    public void updateCurrent(View view){
        selectedEventName.setText("Stinky");
    }
    public void deleteCurrent(View view){

        if (EventReminderList.standardEnabled){
             reminder = helper.searchEventPlacement(EventReminderList.standardEventList,EventReminderList.selectedEvent.getNotification_id() );
        }
        else{
             reminder = helper.searchEventPlacement(EventReminderList.dumpEventList,EventReminderList.selectedEvent.getNotification_id());
        }

        if (reminder == -1){
            Toast.makeText(this,"This event no longer exists!",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,EventReminderList.class);
            startActivity(intent);
        }
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(EventDetails.this);
            builder.setCancelable(true);
            builder.setTitle("Title");
            builder.setMessage("Message");
            builder.setPositiveButton("Confirm",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (EventReminderList.standardEnabled){
                                EventReminderList.standardEventList.remove(reminder);

                            }
                            else {
                                EventReminderList.dumpEventList.remove(reminder);
                            }

                            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                            Intent oldIntent  = new Intent(getApplicationContext(),Notification_receiver.class);
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),reminder,oldIntent,PendingIntent.FLAG_UPDATE_CURRENT);
                            alarmManager.cancel(pendingIntent);

                            Intent intent = new Intent(EventDetails.this,EventReminderList.class);
                            startActivity(intent);

                        }
                    });
            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        }


        }

}




