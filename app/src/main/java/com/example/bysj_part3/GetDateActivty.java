package com.example.bysj_part3;

import java.util.Date;
import java.util.List;
import java.util.zip.Inflater;

import com.example.bysj_czzs3.R;

import entity.Mission;
import entity.MissionGroup;
import entity.MissionsForGroup;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.FinalDb;
import net.tsz.afinal.annotation.view.ViewInject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;
import android.widget.CalendarView.OnDateChangeListener;
/**
 * 
 * @author 张航舰
 *
 * @for(获取日历中日期的方法)
 */
public class GetDateActivty extends FinalActivity{
	@ViewInject(id=R.id.calendarView_getdate)
	private CalendarView calendarView;
	@ViewInject(id=R.id.button_in_canlender,click="doAdd")
	private Button button;
	//从日历控件中获取的年月日
	private int years;
	private int months;
	private int days;
	private int groupId;
	private FinalDb db;
	private MissionGroup group;
	private List<MissionsForGroup> list; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_getdate_part3);
		init();
		calendarView.setOnDateChangeListener(new OnDateChangeListener() {
			
			@Override
			public void onSelectedDayChange(CalendarView v, int year, int month,
					int day) {
				years = year;
				months = month+1;
				days = day;
				Date date = new Date(year, month, day);
				Intent intent = new Intent();
				intent.putExtra("year", year);
				intent.putExtra("month", month);
				intent.putExtra("day", day);
			}
		});
	}
	private void init() {
		db = FinalDb.create(getApplicationContext());
		group = (MissionGroup) getIntent().getSerializableExtra("group");
		//db.save(group);
		groupId = db.findAllByWhere(MissionGroup.class,"comment='"+group.getComment()+"'").get(0).getId();
		list = db.findAllByWhere(MissionsForGroup.class, "MissionGroupId="+groupId);
		Log.d("aa", "bb"+list.toString());
		
	}
	public void doAdd(View v){
		Toast.makeText(getApplicationContext(), 
				years+"年"+months+"月"+days+"日开始这个任务组", 
				Toast.LENGTH_SHORT).show();
		Log.d("aa",years+"aa"+months+"bb"+days );
		Date date = new Date(years, months, days);
		for(int i = 0 ; i <list.size();i++){
			MissionsForGroup forGroup = list.get(i);
			int days = (forGroup.getDays()-1)+(forGroup.getWeeks()-1)*7;
			Mission mission = new Mission();
			mission.setCreatedate(new Date());
			Date dates = new Date(System.currentTimeMillis());
			Log.d("date", dates.toString());
			Date date3 = new Date(System.currentTimeMillis()+days*24*60*60*1000);
			Log.d("date3", date3.toString()+"aa"+date3.getMonth()+date3.getYear()+date3.getDate()+date3.getDay());
			mission.setExcuteDate(date3);
			mission.setComment(forGroup.getComment());
			mission.setContent(forGroup.getContent());
			mission.setIsFinish(false);
			Log.d("missions To Save",mission.toString());
			db.save(mission);
		}
	}
}
