package com.example.bysj_part3;

import java.util.ArrayList;
import java.util.List;

import com.example.bysj_czzs3.R;

import entity.Mission;
import entity.MissionGroup;
import entity.MissionsForGroup;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.FinalDb;
import net.tsz.afinal.annotation.view.ViewInject;

public class Activity_showMissionGroup extends FinalActivity{
	private FinalDb db;
	private List<MissionGroup> list_missionGroup;
	private List<String> list_contents;
	@ViewInject(id=R.id.simple_ListView)
	private ListView listView;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.simpele_list);
		init();
	}
	private void init() {
		db =FinalDb.create(getApplicationContext());
		list_missionGroup = new ArrayList<MissionGroup>();
		list_contents = new ArrayList<String>();
		list_missionGroup = db.findAll(MissionGroup.class);
		if(list_missionGroup.size()==0){
			Toast.makeText(getApplicationContext(), "现在没有本地任务组", Toast.LENGTH_LONG).show();
		}
		Log.d("list_for_MissionGroup",list_missionGroup.toString());
		//获得最简单的stringList将它作为Adapter的数据源
		for(int i = 0 ; i <list_missionGroup.size();i++){
			list_contents.add(list_missionGroup.get(i).getContent());
		}
		//得到了MissionGroup以及MissionForgroup两个表中的数据显示在Logcat里。
		Log.d("list_for_contents", list_contents.toString());
		Log.d("list_for_mission", db.findAll(MissionsForGroup.class).toString());
		//是时候显示数据了
		ArrayAdapter<String> adapter = 
				new ArrayAdapter<String>(getApplicationContext()
						, android.R.layout.simple_list_item_1, list_contents);
		listView.setAdapter(adapter);
		//设置listview的点击事件
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				Intent intent = new Intent(getApplicationContext(), Activity_showMissionGroupDetail.class);
				intent.putExtra("missionGroup", list_missionGroup.get(position));
				startActivity(intent);
			}
		});
	}
}
