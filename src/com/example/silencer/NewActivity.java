package com.example.silencer;

import java.util.HashMap;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class NewActivity extends Activity implements OnItemClickListener {
	private TextView txtStart;
	private TextView txtEnd;
	private TextView txtDaysOfWeek;
	private Dialog listDialog;
	private String[] days;
	private HashMap<Integer, Boolean> daysChecked;
	
	
	private int sMinute;
	private int sHour;
	private int eMinute;
	private int eHour;
	
	static final int TIME_DIALOG_ID = 0; 
	static final int TIME_DIALOG_ID1 = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new);
		
		txtStart = (TextView) findViewById(R.id.txtStart);
		txtEnd = (TextView) findViewById(R.id.txtEnd);
				
		days = getApplicationContext().getResources().getStringArray(R.array.daysOfWeek);
	}
	
	public void cancelNewSchedule()
	{
		Intent in = new Intent(NewActivity.this, MainActivity.class);
		startActivity(in);
	}
	
	public void submitNewSchedule()
	{
		TextView label = (TextView) findViewById(R.id.newLabel);
		TextView startTime = (TextView) findViewById(R.id.txtStart);
		TextView endTime = (TextView) findViewById(R.id.txtEnd);
		
		DBHelper DB = new DBHelper(getApplicationContext());
		
		
	}
	
	public void initDaysOfWeekPopup(View view) {
		listDialog = new Dialog(this);
		listDialog.setTitle("Select Day of Week");
		LayoutInflater li = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = li.inflate(R.layout.popup_listview, null, false);
		listDialog.setContentView(v);
		listDialog.setCancelable(true);
		
		ListView listView = (ListView) listDialog.findViewById(R.id.listView);
		listView.setOnItemClickListener((OnItemClickListener) this);
		listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, days));
		
		listDialog.show();
		daysChecked = new HashMap<Integer, Boolean>();
		
	}
	
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					CheckedTextView item = (CheckedTextView) arg1;
			item.toggle();
			if (item.isChecked()) {
				daysChecked.put(arg2, true);
			} else {
				daysChecked.remove(arg2);
			}
	}
	
	public void daysFinish(View view)
	{
		String dayString = "";
		Log.i("HEre", "We are hgere");
		for(int i = 0; i < 7; i++) {
			Boolean dayIsChecked = daysChecked.get(i);
			if (dayIsChecked != null) {
				dayString += days[i].substring(0, 3) + ", ";
			}
		}
		dayString = dayString.substring(0, dayString.length()-2);
		txtDaysOfWeek = (TextView) findViewById(R.id.txtDaysOfWeek);
		txtDaysOfWeek.setText(dayString);
		listDialog.dismiss();
	}
	
	@SuppressWarnings("deprecation")
	public void openStartTimeDialog(View v) {
		showDialog(TIME_DIALOG_ID);
	}
	@SuppressWarnings("deprecation")
	public void openEndTimeDialog(View v) {
		showDialog(TIME_DIALOG_ID1);
	}	
	
	
	private TimePickerDialog.OnTimeSetListener timePickerListener = 
			new TimePickerDialog.OnTimeSetListener() {
				public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
					sHour = selectedHour;
					sMinute = selectedMinute;
					
					StringBuilder time = setTime("start");
					txtStart.setText(time);
				}
			};
	private TimePickerDialog.OnTimeSetListener timePickerListener2 = 
			new TimePickerDialog.OnTimeSetListener() {
				public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
					eHour = selectedHour;
					eMinute = selectedMinute;
					
					StringBuilder time = setTime("end"); 
					txtEnd.setText(time);
				}
			};
			
	@Override
	protected Dialog onCreateDialog(int id) {
		switch(id) {
		case TIME_DIALOG_ID:
			return new TimePickerDialog(this, timePickerListener, sHour, sMinute, false);
		case TIME_DIALOG_ID1:
			return new TimePickerDialog(this, timePickerListener2, eHour, eMinute, false);
		}
		return null;
	}
	
	private StringBuilder setTime(String startOrEnd) {
		int hour;
		int minute;
		String suffix;
		if (startOrEnd == "start") {
			minute = sMinute;
			hour = sHour;
		} else {
			minute = eMinute;
			hour = eHour;			
		}
		
		if (hour > 12) {
			suffix = "PM";
			hour = hour - 12;
		} else {
			if (hour == 0) {
				hour = 12;
			}
			suffix = "AM";
		}
		
		return new StringBuilder().append(pad(hour)).append(":").append(pad(minute)).append(suffix);
		
	}
	private static String pad(int c) {
		if (c >= 10)
			return String.valueOf(c);
		else
			return "0" + String.valueOf(c);
		
	}	
	
	
}

