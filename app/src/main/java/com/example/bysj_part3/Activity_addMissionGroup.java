package com.example.bysj_part3;

import java.util.ArrayList;
import java.util.List;

import com.example.bysj_czzs3.R;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.FinalDb;
import net.tsz.afinal.annotation.view.ViewInject;

import entity.Mission;
import entity.MissionsForGroup;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Activity_addMissionGroup extends FinalActivity{
	@ViewInject(id=R.id.editText_input_comment)
	private EditText editText_comment;
	@ViewInject(id=R.id.editText_input_content)
	private EditText editText_content;
	@ViewInject(id=R.id.textView_info_missionGroup)
	private TextView textView_info;
	@ViewInject(id=R.id.btn_addMission,click="doAdd")
	private Button button_add;
	@ViewInject(id=R.id.btn_addMission_next,click="doNext")
	private Button button_next;
	@ViewInject(id=R.id.btn_goback,click="goback")
	private Button button_back;
	@ViewInject(id=R.id.spinner_weeks)
	private Spinner spinner_weeks ;
	@ViewInject(id=R.id.spinner_days)
	private Spinner spinner_days;
	//-------------------------组件-----------------------
	private MissionsForGroup missionforGroup;
	private int weeks;
	private int days;
	private int missionGroupId;
	private int current_weeks;//当前添加的周数1-设定的周数
	private int current_days;//当日按添加的日期1-7
	private FinalDb db;
	//--------------------成员-------------------------
	
	public Activity_addMissionGroup() {
		current_weeks = 1;
		current_days = 1;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_real_addmissiongroup);
		init();
	}
	private void init() {
		
		Intent intent = getIntent();//得到包含的意图以及包含的信息
		missionGroupId=Integer.parseInt(intent.getStringExtra("missionGroupId"));
		weeks=Integer.parseInt(intent.getStringExtra("weeks"));
		days = Integer.parseInt(intent.getStringExtra("days"));
		textView_info.setText("这是第"+weeks+"周的第"+days+"天");
		current_days = days;
		current_weeks = weeks;
		db = FinalDb.create(getApplicationContext());
	}
	
	public void doNext(View v){
		if(current_days!=7){
			current_days++;
		}else{
			current_weeks++;
			current_days  =1;
		}
		editText_comment.setText("");
		textView_info.setText("这是第"+current_weeks+"周的第"+current_days+"天");
	}
	
	public void doAdd(View v){
		Log.d("aa", "ccc");
		String content = editText_content.getText().toString();
		String comment = editText_comment.getText().toString();
		Log.d("aa", content+comment);
		missionforGroup = new MissionsForGroup(missionGroupId, content, comment, current_weeks, current_days);
		Log.d("ss", missionforGroup.toString());
		db.save(missionforGroup);
		Log.d("list", db.findAll(MissionsForGroup.class).toString());
		Toast.makeText(getApplicationContext(), "任务已经成功添加", Toast.LENGTH_SHORT).show();
	}
	
	public void goback(View v){
		finish();
	}
}
