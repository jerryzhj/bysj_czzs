package com.example.bysj_part3;

import java.util.ArrayList;
import java.util.List;

import com.example.bysj_czzs3.R;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import utils.MissionGroupAdapter;

import entity.Mission;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ListView;

public class Activity_MissionGroup extends Activity{
	private ExpandableListView listView;
	private List<List<Mission>> data;
	private String content;
	private String comment;
	private String weeks;
	private String filedir;
	private String missionGroupId;
	private MissionGroupAdapter adapter;
	private int groupPosition_alert;
	private int childPosition_alert;
	private TextView textView_AlertDailog_info;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_add_missiongroup);
		listView=(ExpandableListView) findViewById(R.id.expendable_show_MissionGroup);
		init();
		adapter = new MissionGroupAdapter(data,getApplicationContext(),R.layout.item_missgroup_parent,R.layout.item_missiongroup_child);
		listView.setAdapter(adapter);
		setonClickListenner();
	}
	private void setonClickListenner() {
		Log.d("listerner", "123");
		listView.setOnChildClickListener(new OnChildClickListener() {
		@Override
		public boolean onChildClick(ExpandableListView parent, View v, int groupPisition,
				int childPosition, long id) {
			groupPosition_alert = groupPisition;
			childPosition_alert = childPosition;
			Toast.makeText(getApplicationContext(), groupPisition+"aa"+childPosition, 1).show();
			//显示一个提示框来添加任务.----因为不能熟练掌握被抛弃
			//showAlertDailog(getApplicationContext());
			
			//跳转到一个新的活动里去添加任务组中的任务
			startAddMissionActivity();
			return false;
		}
		
	});
		
	}
	protected void startAddMissionActivity() {
		Intent intent = new Intent(getApplicationContext(), Activity_addMissionGroup.class);
		intent.putExtra("missionGroupId",missionGroupId);
		Log.d("aa", missionGroupId);
		intent.putExtra("weeks", ""+(groupPosition_alert+1));
		intent.putExtra("days", ""+(childPosition_alert+1));
		startActivity(intent);
		finish();
	}
//	protected void showAlertDailog(Context context) {
//		 new AlertDialog.Builder(Activity_MissionGroup.this)
//		  	.setTitle("请输入")
//		  	.setIcon(android.R.drawable.ic_dialog_info)
//		  	.setView(editText_comment)
//		  	.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//				
//				public void onClick(DialogInterface arg0, int arg1) {
//					
//				}
//			})
//		  	.setNegativeButton("取消", null)
//		  	.show();
//		
//	}
	private void init() {
		data = new ArrayList<List<Mission>>();
		Intent intent = getIntent();//得到包含的意图以及包含的信息
		content=intent.getStringExtra("content");
		comment =intent.getStringExtra("comment");
		missionGroupId = intent.getStringExtra("missionGroupId");
		Log.d("aa2", missionGroupId);
		weeks = intent.getStringExtra("weeks");
		filedir = intent.getStringExtra("filedir");
//		editText_comment = new EditText(getApplicationContext());
//		editText_content = new EditText(getApplicationContext());
		textView_AlertDailog_info = new TextView(getApplicationContext());
		textView_AlertDailog_info.setText("aaaa");
		Log.d("aa", comment+content+weeks+filedir+"aaaa");
		//实现数据的初始化
		int count = Integer.parseInt(weeks);
		for(int i = 0 ; i <count ; i++){
			List<Mission> list = new ArrayList<Mission>();
			for(int j = 0 ; j <7;j++){
				list.add(new Mission());
			}
			Log.d("list",list.toString());
			data.add(list);
		}
	}
}
