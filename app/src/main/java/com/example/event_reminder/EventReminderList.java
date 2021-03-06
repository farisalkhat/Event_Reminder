package com.example.event_reminder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
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
import com.example.event_reminder.EventReminder.helper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class EventReminderList extends AppCompatActivity {
    private ListView eventListView;

    private RecyclerView mRecyclerView;
    public static EventAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    private ListView importantListView;
    private Button oldEventsButton;
    private Button currentEventsButton;
    String[] menuItems = {};
    SimpleDateFormat format = new SimpleDateFormat("EEEE, MMMM d, yyyy 'at' h:mm a");
    public static ArrayList<EventReminder> standardEventList = new ArrayList<>();
    public static ArrayList<EventReminder> importantEventList = new ArrayList<>();
    public static ArrayList<EventReminder> dumpEventList = new ArrayList<>();
    public static EventReminder selectedEventReminder;
    public static ViewGroup EventReminderList;
    public static int eventPosition;
    public static boolean standardEnabled;
    public static EventReminder selectedEvent;

    public static String tester = "poopy";
    public static SharedPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        SharedPreferencesManager.loadStandardEventList(getApplicationContext());
        SharedPreferencesManager.loadDumpEventList(getApplicationContext());
        SharedPreferencesManager.loadImportantEventList(getApplicationContext());




        ComponentName receiver = new ComponentName(this,Notification_receiver.class);
        PackageManager pm = this.getPackageManager();
        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);

        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_event_reminder_list);

        oldEventsButton = findViewById(R.id.oldEventsButton);
        currentEventsButton = findViewById(R.id.currentEventsButton);

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new EventAdapter(standardEventList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new EventAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                setSelectedEvent(position);
                showScene();
            }

            @Override
            public void onDeleteClick(int position) {
                setSelectedEvent(position);
                deleteEvent();
                mAdapter.notifyItemChanged(position);
            }
        });
    }




    public void loadStandardEvents(){
        SharedPreferences preferences= getApplicationContext().getSharedPreferences("shared preferences",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString("standardEventList", null);

        Type type = new TypeToken<ArrayList<EventReminder>>() {}.getType();
        standardEventList = gson.fromJson(json, type);
        if(standardEventList==null){
            standardEventList = new ArrayList<>();
            Log.i("standard","List was empty, so created new one");
        }
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

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new EventAdapter(standardEventList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new EventAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                setSelectedEvent(position);
                showScene();
            }

            @Override
            public void onDeleteClick(int position) {
                setSelectedEvent(position);
                deleteEvent();
                mAdapter.notifyItemChanged(position);
            }
        });


    }
    public void showOldEvents(View view){
        oldEventsButton.setVisibility(View.INVISIBLE);
        oldEventsButton.setClickable(false);

        currentEventsButton.setVisibility(View.VISIBLE);
        currentEventsButton.setClickable(true);

        mAdapter = new EventAdapter(dumpEventList);
        mRecyclerView.setAdapter(mAdapter);


        mAdapter.setOnItemClickListener(new EventAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                setSelectedEvent(position);
                showScene();
            }

            @Override
            public void onDeleteClick(int position) {
                setSelectedEvent(position);
                deleteEvent();
                mAdapter.notifyItemChanged(position);



            }
        });




        //EventReminderList.invalidate();

    }

    private void setSelectedEvent(int pos){
        eventPosition = pos;
        if(oldEventsButton.isClickable()){
            selectedEvent = standardEventList.get(pos);
            standardEnabled = true;}

        else{
            selectedEvent = dumpEventList.get(pos);
            standardEnabled=false ;}
        Log.i("Event Position:","poopy " + pos);
    }


    private void showScene() {
        Intent intent = new Intent(this, EventDetails.class);
        startActivity(intent);
    }

    private void deleteEvent(){
        int reminder_id = helper.searchEventPlacement(standardEventList,selectedEvent.getNotification_id() );

        if (standardEnabled){
            standardEventList.remove(reminder_id);

            SharedPreferencesManager.saveStandardEventList(getApplicationContext());

            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent oldIntent  = new Intent(getApplicationContext(),Notification_receiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),reminder_id,oldIntent,PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.cancel(pendingIntent);
        }
        else {
            dumpEventList.remove(reminder_id);
            SharedPreferencesManager.saveDumpEventList(getApplicationContext());
        }


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
            TextView titleView = view.findViewById(R.id.poopView);
            TextView dateView = view.findViewById(R.id.poop2view);
            if(dumpEventList.size()>0) {
                titleView.setText(dumpEventList.get(i).getEventName());
                dateView.setText(format.format(dumpEventList.get(i).getEventDate().getTime()));
            }
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
            TextView titleView = view.findViewById(R.id.poopView);
            TextView dateView = view.findViewById(R.id.poop2view);
            if(standardEventList.size()>0) {
                titleView.setText(standardEventList.get(i).getEventName());
                dateView.setText(format.format(standardEventList.get(i).getEventDate().getTime()));
            }
            return view;
        }

    }

}












