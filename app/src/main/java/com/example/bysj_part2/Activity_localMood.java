package com.example.bysj_part2;

import java.util.ArrayList;
import java.util.List;

import com.example.bysj_czzs3.R;

import entity.Mood;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.FinalDb;
import net.tsz.afinal.annotation.view.ViewInject;

public class Activity_localMood extends FinalActivity{
	@ViewInject(id=R.id.list_view_mood)
	private ListView listView;
	private List<Mood> list;
	private List<String> contents;
	private List<String> ids;
	private OnItemClickListener itemClickListener;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_allmood);
		initLists();
		initListner();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(Activity_localMood.this, android.R.layout.simple_list_item_1, contents);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(itemClickListener);
	}
	private void initListner() {
		itemClickListener = new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				Intent intent = new Intent(getApplicationContext(), Activity_MoodDetail.class);
				intent.putExtra("id", ids.get(position));
				startActivity(intent);
			}
		};
		
	}
	private void initLists() {
		contents = new ArrayList<String>();
		ids = new ArrayList<String>();
		list = new ArrayList<Mood>();
		FinalDb db =FinalDb.create(getApplicationContext());
		list = db.findAll(Mood.class);
		if(list.size()==0){
			Toast.makeText(getApplicationContext(), "您现在没有本地心情", Toast.LENGTH_LONG).show();
		}
		for(int i = 0 ; i <list.size();i++){
			contents.add(list.get(i).getContnet());
			ids.add(list.get(i).getId()+"");
		}
	}
}
