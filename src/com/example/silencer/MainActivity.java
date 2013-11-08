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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.silencer.DBHelper;

public class MainActivity extends Activity {
	
	TextView textLat;
	TextView textLong;
	TextView currentLat;
	TextView currentLong;
	DBHelper DB;
	Button btnSetLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        textLat = (TextView)findViewById(R.id.textLat);
        textLong = (TextView)findViewById(R.id.textLong);
        currentLat = (TextView)findViewById(R.id.currentLat);
        currentLong = (TextView)findViewById(R.id.currentLong);
        LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        LocationListener ll = new mylocationlistener();
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, ll);
        
        DB = new DBHelper(this);
        setSavedLocation();
       
    }
        
    public void onToggleService(View view) {
    	boolean on = ((ToggleButton) view).isChecked();
    	
    	if (on) {
    		startService(new Intent(this, GPSService.class));
    	} else {
    		stopService(new Intent(this, GPSService.class));
    	}
    }
    
    private void setSavedLocation() {
        // set previous lat/long
        Cursor row = DB.get();
        
        String nwlat = row.getString(row.getColumnIndex("nwlat"));
        String selat = row.getString(row.getColumnIndex("selat"));
        
        String nwlong = row.getString(row.getColumnIndex("nwlong"));
        String selong = row.getString(row.getColumnIndex("selong"));
        currentLat.setText(nwlat + ", " + selat);
        currentLong.setText(nwlong + ", " + selong); 
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    class mylocationlistener implements LocationListener {

        @Override
        public void onLocationChanged(Location arg0) {
                if(arg0 != null)
                {
                        double pLong = arg0.getLongitude();
                        double pLat = arg0.getLatitude();
                        
                        textLat.setText(Double.toString(pLat));
                        textLong.setText(Double.toString(pLong));
                        
                }
                // TODO Auto-generated method stub
                
        }

        @Override
        public void onProviderDisabled(String arg0) {
                // TODO Auto-generated method stub
                
        }

        @Override
        public void onProviderEnabled(String arg0) {
                // TODO Auto-generated method stub
                
        }

        @Override
        public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
                // TODO Auto-generated method stub
                
        }
    }
}
