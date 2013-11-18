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
		
		public void setLocation(Double coord, String type) {
			ContentValues values = new ContentValues();
			values.put(type, coord);
			this.db.update(COORD_TABLE_NAME, values, null, null);
		}
		
		public void addSchedule(String label, String start_time, String end_time, String days) {
			ContentValues values = new ContentValues();
			values.put(label, label);
			values.put(start_time, start_time);
			values.put(end_time, end_time);
			values.put(days, days);
			this.db.insert(SCHED_TABLE_NAME, null, values);
		}

		public Cursor getLocation() {
			Cursor c = this.db.query(
					COORD_TABLE_NAME, 
					new String[] { "nlat", "wlong" , "slat" , "elong" }, 
					null,
					null,
					null,
					null,
					"nlat");
			c.moveToFirst();
			return c;
		}
		
		public Cursor getSchedule() {
			Cursor c = this.db.rawQuery("SELECT schedule.label, schedule.start_time, schedule.end_time, schedule.day, location.nlat, location.slat, location.wlong, location.elong from " + SCHED_TABLE_NAME + " left outer join " + COORD_TABLE_NAME + " on schedule._id=location.schedule_id order by schedule.day, schedule.start_time desc limit 1", null);
			c.moveToFirst();
			return c;
		}
		
		
		@Override
		
		public void onCreate(SQLiteDatabase db) {

			db.execSQL(
					"create table " + COORD_TABLE_NAME + " (_id integer primary key autoincrement, schedule_id integer not null, nlat decimal not null, wlong decimal not null, slat decimal not null, elong decimal not null)");
			db.execSQL(
					"create table " + SCHED_TABLE_NAME + " (_id integer primary key autoincrement, label text not null, start_time text not null, end_time text not null, day integer null)");

			ContentValues sched_vals = new ContentValues();
			sched_vals.put("label", "Church");
			sched_vals.put("start_time", "10:00");
			sched_vals.put("end_time", "11:30");
			sched_vals.put("day", 1);
			long sched_id = db.insert(SCHED_TABLE_NAME, null, sched_vals);
			ContentValues values = new ContentValues();
			values.put("schedule_id", sched_id);
			values.put("nlat",  40.802489);
			values.put("wlong", -96.605456);
			values.put("slat",  40.801572);
			values.put("elong", -96.604525);
			db.insert(COORD_TABLE_NAME, null, values);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			
		}
		
}
