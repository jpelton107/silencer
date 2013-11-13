package com.example.silencer;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class NewActivity extends Activity {
	private TextView txtStart;
	private TextView txtEnd;
	
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

