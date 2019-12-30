package com.example.event_reminder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class EventDetails extends AppCompatActivity {


    TextView selectedEventDesc;
    TextView selectedEventName;
    TextView selectedEventDate;
    TextView selectedEventImp;

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


}

