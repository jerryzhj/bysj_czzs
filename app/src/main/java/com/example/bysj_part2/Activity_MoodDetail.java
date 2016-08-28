package com.example.bysj_part2;

import java.io.File;

import com.example.bysj_czzs3.R;
import com.squareup.picasso.Picasso;

import entity.Mood;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.FinalDb;
import net.tsz.afinal.annotation.view.ViewInject;

public class Activity_MoodDetail extends FinalActivity{
	@ViewInject(id=R.id.textView_date)
	private TextView textView_date;
	@ViewInject(id=R.id.img_mood)
	private ImageView imageView;
	private Mood mood;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mooddetail);
		init();
	}
	private void init() {
		Intent intent = getIntent();
		String id=intent.getStringExtra("id");
		FinalDb db = FinalDb.create(getApplicationContext());
		mood =db.findById(id, Mood.class);
		Log.d("res", mood.getUrl());
		Picasso.with(getApplicationContext()).load(new File(mood.getUrl())).fit().into(imageView);
		textView_date.setText(mood.getContnet()+"日期是"+mood.getCreatedate().toString());
	}
}
