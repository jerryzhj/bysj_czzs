package com.example.bysj_part4;

import java.util.ArrayList;
import java.util.List;

import com.example.bysj_czzs3.R;
import com.squareup.picasso.Picasso;

import entity.UserInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.FinalDb;
import net.tsz.afinal.annotation.view.ViewInject;

public class Activity_ShowEffect extends FinalActivity{
	@ViewInject(id=R.id.textView1)
	private TextView textView;
	@ViewInject(id =R.id.imageView_showBackGroud)
	private ImageView imageView;
	
	private List<UserInfo> list;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_effect);
		FinalDb db = FinalDb.create(getApplicationContext());
		List<UserInfo>list =db.findAll(UserInfo.class);
		Log.d("list", list.toString());
		init(list);
	}
	private void init(List<UserInfo> list) {
		// TODO Auto-generated method stub
		if(list.equals("")||list.equals(null)){
			textView.setText("欢迎回来,使用者大人");
			imageView.setImageResource(R.drawable.ic_launcher);
		}
		else{
			UserInfo info = list.get(list.size()-1);
			Log.d("myinfo", info.toString());
			textView.setText("欢迎回来,"+info.getNickName());
			switch (info.getPicId()) {
			case 1:
				Picasso.with(getApplicationContext()).load(R.drawable.girl).into(imageView);
				break;
			case 2:
				Picasso.with(getApplicationContext()).load(R.drawable.kaliye).into(imageView);
				break;
			case 3:
				Picasso.with(getApplicationContext()).load(R.drawable.sukura).into(imageView);
				break;
			case 4:
				Picasso.with(getApplicationContext()).load(R.drawable.ling).into(imageView);
				break;
			default:
				break;
			}
			
		}
	}
}
