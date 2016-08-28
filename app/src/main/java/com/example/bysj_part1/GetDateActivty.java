package com.example.bysj_part1;

import java.util.Date;
import java.util.zip.Inflater;

import com.example.bysj_czzs3.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
/**
 * 
 * @author 张航舰
 *
 * @for(获取日历中日期的方法)
 */
public class GetDateActivty extends Activity{
	
	private CalendarView calendarView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_getdate);
		calendarView = (CalendarView) findViewById(R.id.calendarView_getdate);
		calendarView.setOnDateChangeListener(new OnDateChangeListener() {
			
			@Override
			public void onSelectedDayChange(CalendarView v, int year, int month,
					int day) {
				Date date = new Date(year, month, day);
				Intent intent = new Intent();
				intent.putExtra("year", year);
				intent.putExtra("month", month);
				intent.putExtra("day", day);
				GetDateActivty.this.setResult(RESULT_OK, intent);
				GetDateActivty.this.finish();
			}
		});
	}
}
