package com.example.bysj_part1;

import java.util.List;

import com.example.bysj_czzs3.R;


import entity.Mission;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.FinalDb;
import net.tsz.afinal.annotation.view.ViewInject;

public class DatabaseTest extends Activity{
	private Button button;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		button =(Button) findViewById(R.id.button_dball);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Log.d("aa", "bb");
				FinalDb db =FinalDb.create(getApplicationContext());
				List<Mission> list = db.findAll(Mission.class);
				Log.d("missions", list.toString());
				
			}
		});
	}
	
}
