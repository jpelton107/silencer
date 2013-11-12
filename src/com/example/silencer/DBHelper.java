package com.example.silencer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
		private SQLiteDatabase db;
		private static final int DATABASE_VERSION = 1;
		private static final String DB_NAME = "silencer.db";
		private static final String COORD_TABLE_NAME = "location";
		private static final String SCHED_TABLE_NAME = "schedule";
		
		public DBHelper(Context context) {
			super(context, DB_NAME, null, DATABASE_VERSION);
			db = getWritableDatabase();
		}
		
		public void setLocation(String textLat, String textLong) {
			ContentValues values = new ContentValues();
			values.put("lat", textLat);
			values.put("long", textLong);
			this.db.update(COORD_TABLE_NAME, values, null, null);
		}
		
		public void updateLocation() {
			ContentValues values = new ContentValues();
			values.put("lat", 50);
			values.put("long", 51);
			this.db.update(COORD_TABLE_NAME,  values, "1", null);
		}
		public Cursor get() {
			Cursor c = this.db.query(
					COORD_TABLE_NAME, 
					new String[] { "nwlat", "nwlong" , "swlat" , "swlong", "nelat", "nelong", "selat", "selong" }, 
					null,
					null,
					null,
					null,
					"lat");
			c.moveToFirst();
			return c;
		}
		
		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(
					"if not exists create table " + COORD_TABLE_NAME + " (_id integer primary key autoincrement, schedule_id integer not null, nwlat decimal not null, nwlong decimal not null, swlat decimal not null, swlong decimal not null, nelat decimal not null, nelong decimal not null, selat decimal not null, selong decimal not null, foreign key (schedule_id) references " + SCHED_TABLE_NAME + "(_id)");
			db.execSQL(
					"if not exists create table " + SCHED_TABLE_NAME + " (_id integer primary key autoincrement, start_time text not null, end_time text not null, day int null");
			ContentValues sched_vals = new ContentValues();
			sched_vals.put("start_time", "10:00");
			sched_vals.put("end_time", "11:30");
			sched_vals.put("day", 1);
			long sched_id = db.insert(SCHED_TABLE_NAME, null, sched_vals);
			ContentValues values = new ContentValues();
			values.put("schedule_id", sched_id);
			values.put("nwlat",  40.802489);
			values.put("nwlong", -96.605456);
			values.put("swlat",  40.801572);
			values.put("swlong", -96.605456);
			values.put("nelat",  40.802489);
			values.put("nelong", -96.604525);
			values.put("selat",  40.801572);
			values.put("selong", -96.604525);
			db.insert(COORD_TABLE_NAME, null, values);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			
		}
		
}
