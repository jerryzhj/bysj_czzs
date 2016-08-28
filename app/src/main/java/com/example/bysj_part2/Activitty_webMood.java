package com.example.bysj_part2;

import com.example.bysj_czzs3.R;
import com.squareup.picasso.Picasso;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

public class Activitty_webMood extends FinalActivity{
	@ViewInject(id =R.id.img_mood)
	private ImageView imageView;
	@ViewInject(id=R.id.textView_date)
	private TextView textView;
	private String content;
	private String url;
	private String createDate;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mooddetail);
		Intent intent = getIntent();
		content=intent.getStringExtra("content");
		url = intent.getStringExtra("filedir");
		createDate = intent.getStringExtra("createDate");
		String[] dir = url.split("jpg");
		Log.d("url", getString(R.string.host)+dir[0]+"jpg");
		Picasso.with(getApplicationContext()).load(getString(R.string.host)+"files//"+dir[0]+"jpg").fit().into(imageView);
		textView.setText(content+"   时间是:"+createDate);
	}
}
