package com.example.bysj_part3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.bysj_czzs3.R;
import com.squareup.picasso.Picasso;

import entity.MissionGroup;
import entity.MissionsForGroup;

import utils.MissionGroupRealAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.FinalDb;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.annotation.view.ViewInject;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

public class Activity_showMissionGroupDetail extends FinalActivity{
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
	@ViewInject(id=R.id.button_upLoad_missionGroup,click="doUpLoad")
	private Button button_upLoad;
	@ViewInject(id=R.id.button_AddToMissions,click="doAddToMissions")
	private Button button_addToMissions;
	private FinalDb db;
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
		Intent intent =getIntent();
		//实现了seralizable接口的类可以通过intent传递
		group= (MissionGroup) intent.getSerializableExtra("missionGroup");
		db=FinalDb.create(getApplicationContext());
		list = new ArrayList<List<MissionsForGroup>>();
		missionsForGroups = new ArrayList<MissionsForGroup>();
		//实现MissionGroup的数据填充
		textView_comment.setText(group.getComment());
		Log.d("content",group.getComment()+group.getContent());
		textView_content.setText(group.getContent());
		Picasso.with(getApplicationContext()).load(new File(group.getPictureUrl())).fit().into(imageView_pic);
		//完成list的数据填充
		String where="MissionGroupId="+group.getId();
		missionsForGroups = db.findAllByWhere(MissionsForGroup.class,where);
		Log.d("missionForGroup", missionsForGroups.toString());
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
	}
	
	public void doUpLoad(View v){
		AjaxParams params = new AjaxParams();
		try {
			params.put("content", group.toString());
			params.put("createDate",missionsForGroups.toString());
			params.put("profile_picture", new File(group.getPictureUrl()));
			Toast.makeText(getApplicationContext(), "上传成功", 1).show();
		} catch (FileNotFoundException e) {
			Toast.makeText(getApplicationContext(), "找不到文件", 1).show();
			e.printStackTrace();
		}
		FinalHttp fh = new FinalHttp();
		fh.post("http://192.168.43.245:8088/fileUpLoad/servlet/MissionGroupUpLoadService", params, new AjaxCallBack(){
			  //传输中
			  public void onLoading(long count, long current) {
		 				
		 		}
			  //传输成功后
		 		public void onSuccess(String t) {
		 			Toast.makeText(getApplicationContext(), "提交完成", 1).show();
		 		}
		  });
	}
	
	public void doAddToMissions(View v){
		Intent intent = new Intent(getApplicationContext(), GetDateActivty.class);
		intent.putExtra("group", group);
		startActivity(intent);
	}
	//执行过时间设置之后进行数据库保存
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		// TODO Auto-generated method stub
//		super.onActivityResult(requestCode, resultCode, data);
//		int year =data.getExtras().getInt("year");
//		int month = data.getExtras().getInt("month");
//		int day = data.getExtras().getInt("day");
//		Date date = new Date(year, month, day);//在这里作为任务启动的时间
//		month++;
//		
//		
//	}
}
