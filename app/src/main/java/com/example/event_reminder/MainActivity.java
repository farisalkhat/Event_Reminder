package com.example.event_reminder;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {


    public void startFunction(View view){
        EditText myTextField = (EditText)findViewById(R.id.myTextField);
        Log.i("Info",myTextField.getText().toString());

        Toast.makeText(MainActivity.this,"Hi there!",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void showScene(View view){
        Intent intent = new Intent(this, EventReminderList.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the settings_textview; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void addEventReminder(View view){

    }
    public void createEventReminder(View view){}
    public void deleteEventReminder(View view){}
    public void selectEventReminder(View view){}
    public void favoriteEventReminder(View view){}
    public void viewSettings(View view){}
    public void saveSettings(View view){}
    public void cancelSettings(View view){}




}




