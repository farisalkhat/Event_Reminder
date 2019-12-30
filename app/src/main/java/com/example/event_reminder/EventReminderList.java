package com.example.event_reminder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.EventLog;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.event_reminder.EventReminder.EventReminder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class EventReminderList extends AppCompatActivity {
    private ListView eventListView;
    private ListView importantListView;
    private Button oldEventsButton;
    private Button currentEventsButton;
    String[] menuItems = {};
    SimpleDateFormat format = new SimpleDateFormat("EEEE, MMMM d, yyyy 'at' h:mm a");
    public static ArrayList<EventReminder> standardEventList = new ArrayList<EventReminder>();
    public static ArrayList<EventReminder> importantEventList = new ArrayList<EventReminder>();
    public static ArrayList<EventReminder> dumpEventList = new ArrayList<EventReminder>();
    public static EventReminder selectedEventReminder;
    public static ViewGroup EventReminderList;
    public static int eventPosition;
    public static boolean standardEnabled;
    public static EventReminder selectedEvent;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_reminder_list);
        EventReminderList = getWindow().getDecorView().findViewById(android.R.id.content);

        eventListView = findViewById(R.id.eventListView);
        oldEventsButton = findViewById(R.id.oldEventsButton);
        currentEventsButton = findViewById(R.id.currentEventsButton);

        standardEventAdapter standardEventAdapter = new standardEventAdapter();
        eventListView.setAdapter(standardEventAdapter);

        eventListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                showScene(position);
            }
        });

    }





    public void addEventReminder(View view) {
        Intent intent = new Intent(this, eventreminder_add.class);
        startActivity(intent);
    }
    public void showCurrentEvents(View view){
        oldEventsButton.setVisibility(View.VISIBLE);
        oldEventsButton.setClickable(true);

        currentEventsButton.setVisibility(View.INVISIBLE);
        currentEventsButton.setClickable(false);

        standardEventAdapter standardEventAdapter = new standardEventAdapter();
        eventListView.setAdapter(standardEventAdapter);

    }

    public void showOldEvents(View view){
        oldEventsButton.setVisibility(View.INVISIBLE);
        oldEventsButton.setClickable(false);

        currentEventsButton.setVisibility(View.VISIBLE);
        currentEventsButton.setClickable(true);

        dumpEventAdapter dumpEventAdapter = new dumpEventAdapter();
        eventListView.setAdapter(dumpEventAdapter);


        EventReminderList.invalidate();

    }



    private void showScene(int pos) {
        Intent intent = new Intent(this, EventDetails.class);
        eventPosition = pos;
        if(oldEventsButton.isClickable()){
            selectedEvent = standardEventList.get(pos);
            standardEnabled = true;}

        else{
            selectedEvent = dumpEventList.get(pos);
            standardEnabled=false ;}
        Log.i("Event Position:","poopy " + pos);
        startActivity(intent);
    }

    class dumpEventAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return dumpEventList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            view = getLayoutInflater().inflate(R.layout.eventreminder_textview, null);
            TextView titleView = view.findViewById(R.id.titleView);
            TextView dateView = view.findViewById(R.id.dateView);
            titleView.setText(dumpEventList.get(i).getEventName());



            dateView.setText(format.format(dumpEventList.get(i).getEventDate().getTime()));
            return view;
        }

    }

    class standardEventAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return standardEventList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            view = getLayoutInflater().inflate(R.layout.eventreminder_textview, null);
            TextView titleView = view.findViewById(R.id.titleView);
            TextView dateView = view.findViewById(R.id.dateView);
            titleView.setText(standardEventList.get(i).getEventName());



            dateView.setText(format.format(standardEventList.get(i).getEventDate().getTime()));
            return view;
        }

    }

}












