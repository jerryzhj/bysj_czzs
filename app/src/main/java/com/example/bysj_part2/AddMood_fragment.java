package com.example.bysj_part2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

import net.tsz.afinal.FinalDb;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import com.example.bysj_czzs3.R;
import com.squareup.picasso.Picasso;

import entity.Mood;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class AddMood_fragment extends Fragment{
	private ImageView imageView_takePhoto;
	private EditText editText_mood;
	private Button button_submit;
	private Button button_upLoad;
	private Button button_checkMood;
	private Button button_checkWebMood;
	private OnClickListener onClickListener;
	private Uri imageUri;//拍的图片的保存位置
	public static final int TAKE_PHOTO = 1;
	public static final int CROP_PHOTO = 2;
	private String photoName;
	private Context context;
	private Mood mood;
	FinalDb db;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_addmood, container,false);
		context = getActivity();
		initonclickLister();
		initViews(view);
		db = FinalDb.create(context);
		return view;
	}
	private void initonclickLister() {
		onClickListener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String filedir="/sdcard/"+photoName;
				switch (v.getId()) {
				case R.id.button_submitMood:
					//String filedir="/sdcard/"+photoName;
					mood = new Mood();
					mood.setContnet(editText_mood.getText().toString());
					mood.setIsUpLoad(0);
					mood.setCreatedate(new Date().toString());
					mood.setUrl(filedir);
					db.save(mood);
					Log.d("mood", mood.toString());
					Toast.makeText(getActivity(), "心情保存成功", Toast.LENGTH_SHORT);
					break;
				case R.id.button_upLoadMood:
					AjaxParams params = new AjaxParams();
					try {
						
						params.put("content", mood.getContnet());
						params.put("createDate",mood.getCreatedate().toString());
						params.put("profile_picture", new File(filedir));
						Toast.makeText(context, "上传成功", 1).show();
					} catch (FileNotFoundException e) {
						Toast.makeText(context, "找不到文件", 1).show();
						e.printStackTrace();
					}
					FinalHttp fh = new FinalHttp();
					fh.post("http://192.168.43.245:8088/fileUpLoad/servlet/MoodUpLoadService", params, new AjaxCallBack(){
						  //传输中
						  public void onLoading(long count, long current) {
					 				
					 		}
						  //传输成功后
					 		public void onSuccess(String t) {
					 			Toast.makeText(context, "提交完成", 1).show();
					 		}
					  });
					break;
				case R.id.imageView_takePhoto:
					takePhoto(v);
					break;
				case R.id.button_checkMood:
					Intent intent = new Intent(context, Activity_localMood.class);
					startActivity(intent);
					break;
				case R.id.button_checkMood_forweb:
					Intent intent2 = new Intent(context, Activity_netmood.class);
					startActivity(intent2);
					break;
				default:
					break;
				}
				
			}
		};
	}
	
	
	
	//得到相机返回的照片
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		//裁剪图片
		case TAKE_PHOTO:
			if (resultCode == android.app.Activity.RESULT_OK) {
				Intent intent = new Intent("com.android.camera.action.CROP");
				intent.setDataAndType(imageUri, "image/*");
				intent.putExtra("scale", true);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
				startActivityForResult(intent, CROP_PHOTO); // 启动裁剪程序
			}
			break;
		//保存图片
		case CROP_PHOTO:
			if (resultCode == android.app.Activity.RESULT_OK ) {
				try {
					String filedir="/sdcard/"+photoName;
					Bitmap bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(imageUri));
					//imageView_photo.setImageBitmap(bitmap); // 将裁剪后的照片显示出来
					Picasso.with(context).load(new File(filedir)).fit().into(imageView_takePhoto);
					//利用picaso来出来保存好的图片,将刚刚拍摄的图片放入imageview
					
//					Mood mood = new Mood();
//					mood.setCreatedate(new Date().toString());
//					mood.setUrl(filedir);
//					mood.setIsUpLoad(0);
//					mood.setContnet(editText_mood.getText().toString());
//					
//					FinalDb db =FinalDb.create(context);
//					
//					db.save(mood);
//					Log.d("mood2", mood.toString());
					
				} catch (FileNotFoundException e) {
					Toast.makeText(context, "图片未找到",Toast.LENGTH_SHORT).show();
					e.printStackTrace();
				}
			}
			break;
		default:
		}
	}
	
	public void takePhoto(View v){
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
	private void initViews(View view) {
		
		imageView_takePhoto = (ImageView) view.findViewById(R.id.imageView_takePhoto);
		editText_mood = (EditText) view.findViewById(R.id.editText_myMood);
		button_submit = (Button) view.findViewById(R.id.button_submitMood);
		button_upLoad = (Button) view.findViewById(R.id.button_upLoadMood);
		button_checkMood = (Button) view.findViewById(R.id.button_checkMood);
		button_checkWebMood = (Button) view.findViewById(R.id.button_checkMood_forweb);
		button_submit.setOnClickListener(onClickListener);
		button_upLoad.setOnClickListener(onClickListener);
		button_checkWebMood.setOnClickListener(onClickListener);
		imageView_takePhoto.setOnClickListener(onClickListener);
		button_checkMood.setOnClickListener(onClickListener);
	}
}
