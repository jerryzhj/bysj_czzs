package com.example.bysj_part1;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.bysj_czzs3.R;

import net.tsz.afinal.FinalDb;

import entity.Mission;

import utils.MissionAdapter;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class Fragment_misson extends Fragment{
	
	private ExpandableListView listView;
	private MissionAdapter adapter;
	private List<Mission> missionForToday;//今日任务
	private List<Mission> missionForTommorrow;//明日任务
	private List<Mission> missionForSoon;//即将需要完成的任务
	private List<Mission> missionForFarLater;//以后再说
	private Context context;
	private int categoryId;//第一层列表的id
	private int categoryItem_id;//第二层列表的id;
	private FinalDb db;
	private Mission currentMission;
	//---------------------------变量们------------------------------
	public View onCreateView(LayoutInflater inflater,
			 ViewGroup container,  Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_mission, container,false);
		//Toast.makeText(getActivity(), "aa", Toast.LENGTH_SHORT).show();
		listView=(ExpandableListView) view.findViewById(R.id.expandableListView);
		init();
		adapter = new MissionAdapter(missionForToday, missionForTommorrow, missionForSoon, missionForFarLater, context, categoryId, categoryItem_id);
		listView.setAdapter(adapter);
		listView.setOnChildClickListener(new OnChildClickListener() {
			
			@Override
			public boolean onChildClick(ExpandableListView arg0, View arg1, int arg2,
					int arg3, long arg4) {
				TextView textView2 = new TextView(context);
				currentMission = new Mission();
				switch (arg2) {
				case 0:
					currentMission=missionForToday.get(arg3);
					break;
				case 1:
					currentMission=missionForTommorrow.get(arg3);
					break;
				case 2:
					currentMission=missionForSoon.get(arg3);
					break;
				case 3:
					currentMission=missionForFarLater.get(arg3);
					break;
				default:
					break;
				}
				String text = currentMission.getComment()+"\n时间是"+currentMission.getExcuteDate().toString();
				textView2.setText(currentMission.getComment());
				AlertDialog alertDialog=new AlertDialog.Builder(context)
			  	.setTitle("任务完成了吗？")
			  	.setIcon(android.R.drawable.ic_dialog_info)
			  	.setView(textView2)
			  	.setPositiveButton("取消", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int arg1) {
					db.delete(currentMission);
					Toast.makeText(getActivity(), "任务已删除", Toast.LENGTH_LONG).show();
					}
				})
			  	.setNegativeButton("确定", new OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						currentMission.setIsFinish(true);
						Log.d("currentMission", currentMission.toString());
						db.update(currentMission);
						arg0.dismiss();
					}
				}).create();
			  	alertDialog.show();
				return false;
			}
		});
		return view;
		
	}
	private void init() {
		context = getActivity();
		db=FinalDb.create(context);
		categoryId = R.layout.category;
		categoryItem_id = R.layout.category_item;
		missionForFarLater = new ArrayList<Mission>();
		missionForSoon = new ArrayList<Mission>();
		missionForToday = new ArrayList<Mission>();
		missionForTommorrow = new ArrayList<Mission>();
		//导入数据
		importData();
		
	}
	private void importData() {
		List<Mission> missionAll=db.findAll(Mission.class);
		Log.d("length",missionAll.size()+"aa");
		for (int i = 0; i < missionAll.size(); i++) {
			Mission mission = missionAll.get(i);
			Date date = new Date();
			Long nowTime = date.getTime();
			Long excuteTime = mission.getExcuteDate().getTime();
			//判断是否是本月完成的
			if(mission.getExcuteDate().getYear()>=date.getYear()
				&&mission.getExcuteDate().getMonth()>=date.getMonth()){
				if(mission.getExcuteDate().getDate()==date.getDate()){
					//加入今天执行的任务
					missionForToday.add(mission);
				}else if(mission.getExcuteDate().getDate()==date.getDate()+1){
					//加入明天要执行的任务
					missionForTommorrow.add(mission);
				}else if(mission.getExcuteDate().getDate()>=date.getDate()){
					missionForSoon.add(mission);
				}
			}else{
				missionForFarLater.add(mission);
			}
			
		}
		
	}
}
