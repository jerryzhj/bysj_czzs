package com.example.bysj_part3;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.example.bysj_czzs3.R;

import utils.MyStringUtil;

import entity.MissionGroup;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

public class Activity_NetMissionGroup extends FinalActivity{
	protected static final int DETAIL = 365;
	@ViewInject(id=R.id.simple_ListView)
	private ListView listView;
	private List<MissionGroup> list; 
	private String[] content;
	private Handler handler =new Handler(){
		public void handleMessage(Message msg) {
			if(msg.what==DETAIL){
				String str = msg.obj.toString();
				Log.d("str", str);
				list = new ArrayList<MissionGroup>();
				list = MyStringUtil.getMissionGroups(str);
				Log.d("list", list.toString());
				content =new String[list.size()];
				for(int i = 0 ; i <list.size();i++){
					content[i] = list.get(i).getContent(); 
				}
				
				ArrayAdapter<String> adapter = 
						new ArrayAdapter<String>(getApplicationContext()
								, android.R.layout.simple_list_item_1, content);
				listView.setAdapter(adapter);
			}
		};
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.simpele_list);
		setAdapter();
		setOnclickLisener();
	}
	private void setOnclickLisener() {
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(getApplicationContext(),Activity_NetMissionGroupDetail.class);
				intent.putExtra("missionGroup", list.get(arg2));
				startActivity(intent);
			}
		});
		
	}
	private void setAdapter() {
		Thread thread = new Thread(new Runnable() {
			//网络操作要在主线程之外进行
			public void run() {
				HttpURLConnection connection = null;
				try {
					String ur=getString(R.string.host)+"/servlet/MissionGroupGetAllService";
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
}
