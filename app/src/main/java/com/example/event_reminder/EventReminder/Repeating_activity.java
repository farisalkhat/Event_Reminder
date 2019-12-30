package com.example.event_reminder.EventReminder;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.event_reminder.EventReminderList;
import com.example.event_reminder.R;

import org.w3c.dom.Text;

public class Repeating_activity extends AppCompatActivity {
    TextView eventTitle ;
    TextView eventDesc ;
    TextView eventImportance ;

    TextView upcomingEventTitle ;
    TextView upcomingEventDesc ;
    TextView upcomingEventImportance ;
    TextView upcomingEventTime;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repeating_activity_layout);
        eventTitle = findViewById(R.id.selectedEventTitle);
        eventDesc = findViewById(R.id.selectedEventDesc);
        eventImportance = findViewById(R.id.selectedEventImp);

        upcomingEventTitle = findViewById(R.id.upcomingEventTitle);
        upcomingEventDesc = findViewById(R.id.upcomingEventDesc);
        upcomingEventImportance = findViewById(R.id.upcomingEventImp);
        upcomingEventTime = findViewById(R.id.upcomingEventTime);

        onNewIntent(getIntent());
    }





    @Override
    public void onNewIntent(Intent intent){
        Log.i("aaaaaaaa","poooop");
        Bundle extras = intent.getExtras();
        if(extras!=null){
            if(extras.containsKey("NOTIFICATION_ID")){
                int notification_id = extras.getInt("NOTIFICATION_ID");
                Log.i("notification_id","this: " + notification_id);
                EventReminder reminder = helper.searchEvents(EventReminderList.dumpEventList, notification_id);
                if (reminder==null){
                    Log.i("Event Error","Could not find event");
                }
                else{
                    eventTitle.setText(reminder.getEventName());
                    eventDesc.setText(reminder.getEventDescription());
                    eventImportance.setText(reminder.getEventImportance());


                    if(EventReminderList.standardEventList.size()!=0){
                        upcomingEventTitle.setText(EventReminderList.standardEventList.get(0).getEventName());
                        upcomingEventDesc.setText(EventReminderList.standardEventList.get(0).getEventDescription());
                        upcomingEventImportance.setText(EventReminderList.standardEventList.get(0).getEventImportance());
                        upcomingEventTime.setText(EventReminderList.standardEventList.get(0).getEventDate().getTime().toString());
                    }

                    else{
                        upcomingEventTitle.setText("None!");
                        upcomingEventDesc.setText("");
                        upcomingEventImportance.setText("");
                        upcomingEventTime.setText("");
                    }
                }

            }
        }
    }


}
