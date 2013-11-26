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
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.silencer.DBHelper;

public class MainActivity extends Activity {
	
	Cursor schedules;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // setup table
        TableLayout table = (TableLayout) findViewById(R.id.mainTable);
        
        // grab db values
        DBHelper DB = new DBHelper(this);
        Cursor schedules = DB.getSchedules();
        
        int i = 0;
        do{
        	TableRow tr = new TableRow(this);
        	tr.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
        	TextView label = new TextView(this);
        	label.setText(schedules.getString(schedules.getColumnIndex("label")));
        	tr.addView(label);
        	table.addView(tr);

        }while (schedules.moveToNext());
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
