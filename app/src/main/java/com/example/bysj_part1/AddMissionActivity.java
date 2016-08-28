package com.example.bysj_part1;

import java.util.Date;
import java.util.List;

import com.example.bysj_czzs3.R;


import entity.Mission;
import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.FinalDb;
import net.tsz.afinal.annotation.view.ViewInject;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 
 * @author 张航舰
 *
 * @for(支持添加任务的activity)
 */
public class AddMissionActivity extends FinalActivity{
	private OnTouchListener listener;
	@ViewInject(id=R.id.btn_addmission,click="doAddMission")
	private Button button;
	@ViewInject(id=R.id.editText_MyMission)
	private EditText editText_mission;
	@ViewInject(id=R.id.editText_addComment)
	private EditText editText_comment;
	@ViewInject(id=R.id.TextView_ShowExcuteDate)
	private TextView textView_showExcuteDate;
	private Mission mission;
	public AddMissionActivity() {
		mission = new Mission();
		//设置位置选择器
		listener = new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				Log.d("aa","bb");
				float x = event.getX();
				float y = event.getY();
				//通过判断x和y的值来控制显示效果和任务信息
				if(y>500&&y<850){
					if(x<350){
						editText_mission.setText("好好学习");
						editText_comment.setText("学习之后的放松更有意义");
					}else if(x<700){
						editText_mission.setText("努力锻炼");
						editText_comment.setText("为了健康的身体");
					}else {
						editText_mission.setText("出门玩耍");
						editText_comment.setText("走出家门，感受这个美丽的世界");
					}
				}
				if(y>1150&&y<1500){
					if(x<350){
						Date date = new Date();
						
						mission.setExcuteDate(date);
						int year =date.getYear()+1900;
						int month =date.getMonth()+1;
						int day = date.getDate();
						Log.d("bb",day+"aa"+month);
						Log.d("aa", date.toString());
						textView_showExcuteDate.setText("您将在"+year+"年"+month+"月"+day+"日"+"开始这项任务");
					}else if(x<700){
						Date date = new Date(System.currentTimeMillis()+86400000);
						mission.setExcuteDate(date);
						
						int year =date.getYear()+1900;
						int month =date.getMonth()+1;
						int day = date.getDate();
						Log.d("bb",day+"aa"+month);
						Log.d("aa", date.toString());
						textView_showExcuteDate.setText("您将在"+year+"年"+month+"月"+day+"日"+"开始这项任务");
					}else {
						doGetDate();
					}
				}
				return false;
			}

			private void doGetDate() {
				Intent intent = new Intent(getApplicationContext(), GetDateActivty.class);
				startActivityForResult(intent, 1);
			}
		};
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		int year =data.getExtras().getInt("year");
		int month = data.getExtras().getInt("month");
		int day = data.getExtras().getInt("day");
		Date date = new Date(year, month, day);
		month++;
		textView_showExcuteDate.setText("您将在"+year+"年"+month+"月"+day+"日"+"开始这项任务");
		mission.setExcuteDate(date);
	}
	public void doAddMission(View v){
		String content = editText_mission.getText().toString();
		Log.d("content", content);
		Date date = new Date();
		String comment = editText_comment.getText().toString();
		if(content==null||content.equals("")){
			Toast.makeText(getApplicationContext(), "没有任务添加不了的啊", Toast.LENGTH_LONG).show();
		}else{
			mission.setContent(content);
			mission.setCreatedate(date);
			mission.setComment(comment);
			mission.setIsFinish(false);
			Log.d("aa",mission.toString());
			//利用finaldb来实现保存实例
			FinalDb db = FinalDb.create(getApplicationContext());
			db.save(mission);
			Toast.makeText(getApplicationContext(), "添加任务成功", Toast.LENGTH_SHORT).show();
			mission = new Mission();
		}
	}
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addmission);
		//找到布局
		LinearLayout v=(LinearLayout) findViewById(R.id.LinearLayout_addMission);
		v.setBackgroundResource(R.drawable.background_addmission1);
		v.setOnTouchListener(listener);
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}
}
