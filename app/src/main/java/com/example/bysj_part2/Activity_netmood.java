package com.example.bysj_part2;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.ls.LSInput;

import com.example.bysj_czzs3.R;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;
import entity.Mood;
import android.app.Activity;
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

public class Activity_netmood extends FinalActivity{
	protected static final int ALLMood = 2345;//为hander机制随机取得一个
	@ViewInject(id=R.id.list_view_mood)
	private ListView listView;
	private List<Mood> list;
	private List<String> contents;
	private List<String> ids;
	private OnItemClickListener itemClickListener;
	private Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			if(msg.what==ALLMood){
				String str = msg.obj.toString();
				Log.d("str", str);
				list = new ArrayList<Mood>();
				contents = new ArrayList<String>();
				list = utils.Util4bysj_part2.getAllMood(str);
				//得到contents的list
				for(int i = 0 ; i <list.size();i++){
					contents.add(list.get(i).getContnet());
				}
				//设置adapter
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(Activity_netmood.this, android.R.layout.simple_list_item_1, contents);
				listView.setAdapter(adapter);
			}
		};
	};
	
	
	
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_allmood);
		initLists();
		initListner();
//		ArrayAdapter<String> adapter = new ArrayAdapter<String>(Activity_netmood.this, android.R.layout.simple_list_item_1, contents);
//		listView.setAdapter(adapter);
//		listView.setOnItemClickListener(itemClickListener);
	}
	private void initListner() {
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int possition,
					long arg3) {
				Mood mood = list.get(possition);
				Intent intent = new Intent(getApplicationContext(), Activitty_webMood.class);
				intent.putExtra("content",mood.getContnet());
				intent.putExtra("createDate", mood.getCreatedate());
				intent.putExtra("filedir", mood.getUrl());
				startActivity(intent);
			}
		});
		
	}
	private void initLists() {
		Thread thread = new Thread(new Runnable() {
			//网络操作要在主线程之外进行
			public void run() {
				HttpURLConnection connection = null;
				try {
					String ur=getString(R.string.host)+"servlet/MoodGetAllService";
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
						message.what=ALLMood;
						message.obj=line;
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
