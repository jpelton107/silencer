package com.example.silencer;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class GPSService extends Service {
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startID)
	{
		Timer getUpdatesNow = new Timer();
		Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
		Updates updates = new Updates(getUpdatesNow);
		getUpdatesNow.schedule(updates, 5000);
			
		return START_STICKY;
	}
	
	public class Updates extends TimerTask implements LocationListener {
		private Timer getUpdatesNow;
		double Long;
		double Lat;
		public Updates(Timer newGetUpdatesNow) {
			super();
			getUpdatesNow = newGetUpdatesNow;
			Log.i("Service", "Running");
			LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
	        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 1, (LocationListener) this);
	        
	        Log.i("Lattitude", Double.toString(Lat));
	        Log.i("Longitutde", Double.toString(Long));
			
		}
		public void run() {
			
		}
		@Override
		public void onLocationChanged(Location arg0) {
			if(arg0 != null)
			{
				Long = arg0.getLongitude();
				Lat = arg0.getLatitude();
				Log.i("Location changed:", Double.toString(Long) + " : " + Double.toString(Lat));
		        DBHelper DB = new DBHelper(getApplicationContext());
		        Cursor row = DB.get();
		        

		        Double nelat = row.getDouble(row.getColumnIndex("nelat"));
		        Double swlat = row.getDouble(row.getColumnIndex("swlat"));
		        Double swlong = row.getDouble(row.getColumnIndex("swlong"));
		        Double selong = row.getDouble(row.getColumnIndex("selong"));
		        
		        if ((Long > swlong && Long < selong && Lat > swlat && Lat < nelat)) {
		        	Log.i("Service", "Silenece Phone!");
		        }
			}
			
		}

		@Override
		public void onProviderDisabled(String arg0) {
			Log.i("Service", "GPS Disabled");
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
	@Override
	public void onDestroy()
	{
		Toast.makeText(this, "Service Stopped", Toast.LENGTH_LONG).show();
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
