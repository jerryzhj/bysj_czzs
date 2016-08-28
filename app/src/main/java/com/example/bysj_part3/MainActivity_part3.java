package com.example.bysj_part3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import com.example.bysj_czzs3.R;
import com.squareup.picasso.Picasso;

import entity.MissionGroup;
import entity.MissionsForGroup;


import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.FinalDb;
import net.tsz.afinal.annotation.view.ViewInject;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.Toast;

public class MainActivity_part3 extends FinalActivity {
	
	@ViewInject(id=R.id.editText1)
	private EditText editText_weeks;
	@ViewInject(id=R.id.editText2)
	private EditText editText_content;
	@ViewInject(id=R.id.editText3)
	private EditText editText_comment;
	@ViewInject(id=R.id.button_submit,click="doSubmit")
	private Button button_submit;
	@ViewInject(id=R.id.imageView_missionGroup,click="getPhoto")
	private ImageView imageView;
//---------------------------注册组件-------------------------
	private String picUrl;
	private Uri imageUri;//拍的图片的保存位置
	public static final int TAKE_PHOTO = 1;
	public static final int CROP_PHOTO = 2;
	private String photoName;
	private FinalDb db;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_part3);
	}

	public void doSubmit(View v){
		//Intent intent = new Intent(getApplicationContext(),Activity_addMissionGroup.class);
		Intent intent = new Intent(getApplicationContext(),Activity_MissionGroup.class);
		//向意图里注入信息，传输到添加任务组的活动里去
		String filedir="/sdcard/"+photoName;
		String comment=editText_comment.getText().toString();
		String content= editText_content.getText().toString();
		intent.putExtra("content", content);
		intent.putExtra("comment", comment);
		intent.putExtra("weeks", editText_weeks.getText().toString());
		intent.putExtra("filedir",filedir);
		//新建一个数据库MissionGroup并添加一个实体类MissionGroup
		MissionGroup missionGroup = new MissionGroup(filedir, comment, content);
		Log.d("group", missionGroup.toString());
		db=FinalDb.create(getApplicationContext());
		db.save(missionGroup);
		Log.d("content", ""+content);
		//数据库的测试方法
		//List<MissionGroup> bb = db.findAll(MissionGroup.class);
		//List<MissionGroup> aa =db.findAllByWhere(MissionGroup.class, "content='"+content+"'");
		//Log.d("aa", aa.toString());
		//Log.d("bb", bb.toString());
		int missionGroupId2=db.findAllByWhere(MissionGroup.class, "content='"+content+"'").get(0).getId();
		String missionGroupId= ""+missionGroupId2;
		Log.d("id", ""+missionGroupId);
		intent.putExtra("missionGroupId",missionGroupId);
		Toast.makeText(getApplicationContext(), "任务组添加成功", Toast.LENGTH_LONG).show();
		Log.d("aa", missionGroupId);
		Log.d("missionGroup", db.findAll(MissionGroup.class).toString());
		startActivity(intent);
	}
	//得到照片的方法
	public void getPhoto(View v){
		photoName="tempImage"+System.currentTimeMillis()+".jpg";
		File outputImage = new File(Environment
				.getExternalStorageDirectory(), photoName);
		try {
			if (outputImage.exists()) {
				outputImage.delete();
			}
			outputImage.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		imageUri = Uri.fromFile(outputImage);
		Intent intent = new Intent();
		intent.setAction("android.media.action.IMAGE_CAPTURE");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		startActivityForResult(intent,CROP_PHOTO);//直接跳到保存图片的intent
	}
	//得到相机程序返回的图片
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		//裁剪图片
		case TAKE_PHOTO:
			if (resultCode == RESULT_OK) {
				Intent intent = new Intent("com.android.camera.action.CROP");
				intent.setDataAndType(imageUri, "image/*");
				intent.putExtra("scale", true);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
				startActivityForResult(intent, CROP_PHOTO); // 启动裁剪程序
			}
			break;
		//保存图片
		case CROP_PHOTO:
			if (resultCode == RESULT_OK) {
				try {
					String filedir="/sdcard/"+photoName;
					Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
					//imageView_photo.setImageBitmap(bitmap); // 将裁剪后的照片显示出来
					Picasso.with(getApplicationContext()).load(new File(filedir)).fit().into(imageView);
					//利用picaso来出来保存好的图片,将刚刚拍摄的图片放入imageview
				} catch (FileNotFoundException e) {
					Toast.makeText(getApplicationContext(), "图片未找到",Toast.LENGTH_SHORT).show();
					e.printStackTrace();
				}
			}
			break;
		default:
			break;
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Intent intent = new Intent(getApplicationContext(), Activity_showMissionGroup.class);
			startActivity(intent);
		}
		//if (id == R.id.action_settings2) {
//			Intent intent = new Intent(getApplicationContext(), Activity_NetMissionGroup.class);
//			startActivity(intent);
		//}
		return super.onOptionsItemSelected(item);
	}
}
