package com.example.event_reminder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Settings extends AppCompatActivity {
    private ListView listView;
    String[] menuItems = {"General","Notifications","About","FAQ"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.settings_textview,menuItems);
        listView =  findViewById(R.id.settingsView);
        listView.setAdapter(adapter);

    }
}
