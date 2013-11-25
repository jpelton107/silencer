/*
 * Need to setup 3 activities:
 *  +----------------------+
 * 	+ display all schedules|
 *  +----------------------+
 *  - add new schedule
 *  - edit already existing schedule
 * 
 */

package com.example.silencer;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.silencer.DBHelper;

public class MainActivity extends Activity {
	
	TextView currentLat;
	TextView currentLong;
	TextView scheduleLabel;
	EditText startTime;
	EditText endTime;
	DBHelper DB;
	Button btnSetLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentLat = (TextView)findViewById(R.id.currentLat);
        currentLong = (TextView)findViewById(R.id.currentLong);
        
        scheduleLabel = (TextView)findViewById(R.id.scheduleLabel);
        
        startTime = (EditText)findViewById(R.id.startTime);
        endTime = (EditText)findViewById(R.id.endTime);
        
        DB = new DBHelper(this);
        setCurrentSchedule();
       
    }
    
        
    // TODO: change from toggle, to two button setup
    public void onToggleService(View view) {
    	boolean on = ((ToggleButton) view).isChecked();
    	
    	if (on) {
    		startService(new Intent(this, GPSService.class));
    	} else {
    		stopService(new Intent(this, GPSService.class));
    	}
    }
    
    private void setCurrentSchedule() {
        // set previous lat/long
    	Cursor row = DB.getSchedule();
       
        String slat = row.getString(row.getColumnIndex("slat"));
        String nlat = row.getString(row.getColumnIndex("nlat"));
        String wlong = row.getString(row.getColumnIndex("wlong"));
        String elong = row.getString(row.getColumnIndex("elong"));
        currentLat.setText(nlat + ", " + slat);
        currentLong.setText(wlong + ", " + elong); 
        
        String start = row.getString(row.getColumnIndex("start_time"));
        String end = row.getString(row.getColumnIndex("end_time"));
        Integer day = row.getInt(row.getColumnIndex("day"));
        String label = row.getString(row.getColumnIndex("label"));
        
        scheduleLabel.setText(label);
        startTime.setText(start);
        endTime.setText(end);
        
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.main, menu);        
        return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch(item.getItemId()) {
    	case R.id.action_new:
    		Intent in = new Intent(MainActivity.this, New.class);
    		startActivity(in);
    	default:
    		return super.onOptionsItemSelected(item);
    	}    	
    }
}
