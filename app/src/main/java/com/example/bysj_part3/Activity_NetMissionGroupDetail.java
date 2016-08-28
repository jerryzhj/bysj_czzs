package com.example.bysj_part3;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import utils.MissionGroupRealAdapter;
import utils.MyStringUtil;

import com.example.bysj_czzs3.R;
import com.squareup.picasso.Picasso;

import entity.MissionGroup;
import entity.MissionsForGroup;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.FinalDb;
import net.tsz.afinal.annotation.view.ViewInject;

public class Activity_NetMissionGroupDetail extends FinalActivity{
	protected static final int DETAIL = 129;
	private MissionGroupRealAdapter adapter;
	private MissionGroup group;
	private List<List<MissionsForGroup>> list;
	private List<MissionsForGroup> missionsForGroups;
	//-------------------------以下是组件部分-------------------------
	@ViewInject(id=R.id.ExpendableListView_show_MissionGroup)
	private ExpandableListView listView_all_MissionGroup;
	@ViewInject(id=R.id.TextView_MissionGroups_content)
	private TextView textView_content;
	@ViewInject(id=R.id.TextView_MissionGroups_comment)
	private TextView textView_comment;
	@ViewInject(id=R.id.ImageView_missionGroupPic)
	private ImageView imageView_pic;
	@ViewInject(id=R.id.button_upLoad_missionGroup,click="doNothing")
	private Button button_upLoad;
	@ViewInject(id=R.id.button_AddToMissions,click="doAddToMissions")
	private Button button_addToMissions;
	private FinalDb db;
	private Boolean isDownLoad;
	private int groupId;
	
	private Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			if(msg.what==DETAIL){
				missionsForGroups = MyStringUtil.getAllMissions(msg.obj.toString(), group.getId());
				int Max=0;
				//得到周数,每一周有七天，作为二重循环
				for(int i = 0 ; i <missionsForGroups.size();i++){
					MissionsForGroup missionsForGroup = missionsForGroups.get(i);
					if(missionsForGroup.getWeeks()>Max){
						Max = missionsForGroup.getWeeks();
					}
				}
				//使用了不理想的三重循環來添加數據,但因為底層循環次數少，可以勉強接受
				Log.d("aa", Max+"");
				for(int i = 0 ; i <Max;i++){
					List<MissionsForGroup> list_days = new ArrayList<>();
					for(int j= 0 ; j<7;j++){
						MissionsForGroup missionToAdd=new MissionsForGroup();
						for(int k = 0 ; k <missionsForGroups.size();k++){
							MissionsForGroup missionsForGroup = missionsForGroups.get(k);
							if(missionsForGroup.getWeeks()==i+1&&missionsForGroup.getDays()==j+1){
								missionToAdd = missionsForGroup;
								break;
							}
						}
						list_days.add(missionToAdd);
					}
					list.add(list_days);
				}
				adapter = new MissionGroupRealAdapter(
						list, getApplicationContext(),
						R.layout.item_missgroup_parent, 
						R.layout.item_missiongroup_newchild);
				listView_all_MissionGroup.setAdapter(adapter);
				
			}
		};
	};
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_missiongroup_detail);
		init();
		initAdapter();
	}
	private void initAdapter() {
		
		adapter = new MissionGroupRealAdapter(
				list, getApplicationContext(),
				R.layout.item_missgroup_parent, 
				R.layout.item_missiongroup_newchild);
		listView_all_MissionGroup.setAdapter(adapter);
	}
	private void init() {
		isDownLoad = false;
		db = FinalDb.create(getApplicationContext());
		button_upLoad.setText("下载");
		Intent intent =getIntent();
		//实现了seralizable接口的类可以通过intent传递
		group= (MissionGroup) intent.getSerializableExtra("missionGroup");
		list = new ArrayList<List<MissionsForGroup>>();
		missionsForGroups = new ArrayList<MissionsForGroup>();
		//实现MissionGroup的数据填充
		textView_comment.setText(group.getComment());
		textView_content.setText(group.getContent());
		Picasso.with(getApplicationContext()).load(new File(group.getPictureUrl())).fit().into(imageView_pic);
		doNetWork();
	}
	private void doNetWork() {
		Thread thread = new Thread(new Runnable() {
			//网络操作要在主线程之外进行
			public void run() {
				HttpURLConnection connection = null;
				try {
					String ur=getString(R.string.host)+"/servlet/MissionGroupGetAllService?id="+group.getId();
					URL url = new URL(ur);
					connection =(HttpURLConnection) url.openConnection();
					connection.setRequestMethod("GET");
					connection.setConnectTimeout(5000);
					InputStream in = connection.getInputStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(in));
					StringBuilder response=new StringBuilder();
					String line;
					while ((line = reader.readLine())!=null) {
						response.append(line);
						Log.d("aa", line);
						Message message = new Message();
						message.what=DETAIL;
						message.obj=line;
						Log.d("eeee", line);
						handler.sendMessage(message);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					if(connection!=null)
						connection.disconnect();
				}
			}
		});
		thread.start();
	}
	
	public void doNothing(View v){
		//Toast.makeText(getApplicationContext(), "已经存储至本地", Toast.LENGTH_SHORT).show();
		String content=group.getComment();
		Log.d("aa", "download");
		db.save(group);
		Log.d("aaaaa", db.findAll(MissionGroup.class).toString());
		groupId = db.findAllByWhere(MissionGroup.class, "comment='"+content+"'").get(0).getId();
		Log.d("group", group.toString());
		for(int i=0;i<missionsForGroups.size();i++){
			MissionsForGroup forGroup = missionsForGroups.get(i);
			forGroup.setMissionGroupId(groupId);
			db.save(forGroup);
		}
		Log.d("part3_missiongroup", missionsForGroups.toString());
		isDownLoad = true;
		Toast.makeText(getApplicationContext(), "已经存储至本地", Toast.LENGTH_SHORT).show();
	}
	public void doAddToMissions(View v){
		if(isDownLoad){
		List<MissionsForGroup> aa = db.findAll(MissionsForGroup.class);
		Log.d("myList", db.findAll(MissionGroup.class).toString());
		Intent intent = new Intent(getApplicationContext(), GetDateActivty.class);
		intent.putExtra("group", group);
//		intent.putExtra("groupId", groupId+"");
		startActivity(intent);
		}else{
			Toast.makeText(getApplicationContext(), "请先保存至本地之后再使用本功能", Toast.LENGTH_SHORT).show();
		}
	}
}
