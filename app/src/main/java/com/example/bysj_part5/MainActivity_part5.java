package com.example.bysj_part5;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import com.example.bysj_czzs3.R;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import utils.ApkUtil;

import entity.Apk;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity_part5 extends FinalActivity {
	protected static final int ALLApk = 2233;
	private ListView listview;
	private List<Apk> list;
	private List<String> contents;
	private static String name;
	private String target;
	private String url;
	//对获取到的字符串进行处理
	private Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			if(msg.what==ALLApk){
				String str = msg.obj.toString();
				Log.d("str", str);
				list = ApkUtil.getAllApk(str);
				Log.d("list", list.toString());
				contents = new ArrayList<>();
				for(int i = 0 ; i <list.size();i++){
					contents.add(list.get(i).getName());
				}
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, contents);
				listview.setAdapter(adapter);
			}
		};
	};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_part5);
		listview = (ListView) findViewById(R.id.ListView_otherApps);
		name="";
		initLists();
		SetOnclickLister();
	}

	private void SetOnclickLister() {
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				//Toast.makeText(getApplicationContext(),"这个apk是"+list.get(position).toString(), Toast.LENGTH_LONG).show();
				String action2=list.get(position).getaction();
				String action = action2.split("]")[0];
				name = list.get(position).getUrl();
				Log.d("MyAction", action);
				Intent intent = new Intent(action);
				try {
					startActivity(intent);
				} catch (Exception e) {
//					url = getString(R.string.host)+"apks/"+list.get(position).getUrl()+".apk";
//					target="/sdcard/"+name+".apk";
//					Log.d("url", url);
//					Log.d("target", target);
//					Intent intent3 = new Intent(Intent.ACTION_VIEW);
//					intent3.setData(Uri.parse(url));
//					startActivity(intent3);
//					Thread thread = new Thread(new Runnable() {
//						
//						@Override
//						public void run() {
//							doDownLoad(url,target);
//						}
//					});
					
					//Toast.makeText(getApplicationContext(), "下载完成", Toast.LENGTH_LONG).show();
					//跳入安装环节
					String str = "/"+name+".apk"; 
					String fileName = Environment.getExternalStorageDirectory() + str;
					Log.d("fileName", fileName);
					Intent intent2 = new Intent(Intent.ACTION_VIEW);
					intent2.setDataAndType(Uri.fromFile(new File(fileName)), "application/vnd.android.package-archive");
					startActivity(intent2);
				}
			}
		});
	}

	protected void doDownLoad(String url, String target) {
		String dirName = "";
		dirName = Environment.getExternalStorageDirectory()+"/MyDownload/";
		File f = new File(dirName);
		if(!f.exists()){
			f.mkdir();
		}
		String fileName =dirName+name+".apk";
		File file = new File(fileName);
		if(file.exists()){
			file.delete();
		}
		try {
			URLConnection con = new URL(url).openConnection();
			InputStream is = con.getInputStream();
			byte[] bs = new byte[1024];
			int len;
			OutputStream os = new FileOutputStream(fileName);
			while ((len = is.read(bs)) != -1) {
				os.write(bs, 0, len);
				}
			os.close();
			is.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     }

	//	private static void saveCroppedImage() {
//		String filePath2="/sdcard/myApks";
//        File file2 = new File(filePath2);
//        String filePath=filePath2+"/"+name+".apk";
//        File file = new File(filePath);
//        if (!file2.exists())
//            file2.mkdir();
//        if (!file.exists())
//            file.mkdir();
//        file = new File("/sdcard/temp.apk".trim());
//        String fileName = file.getName();
//        String mName = fileName.substring(0, fileName.lastIndexOf("."));
//        String sName = fileName.substring(fileName.lastIndexOf("."));
//        String newFilePath = filePath + "/" + name+".apk";
//        file = new File(newFilePath);
//        try {
//            file.createNewFile();
//            FileOutputStream fos = new FileOutputStream(file);
//            fos.flush();
//            fos.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//            
//        }
//    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
//			String str = "/zhj_games_pintu.apk"; 
//			String fileName = Environment.getExternalStorageDirectory() + str;
//			Intent intent = new Intent(Intent.ACTION_VIEW);
//			intent.setDataAndType(Uri.fromFile(new File(fileName)), "application/vnd.android.package-archive");
//			startActivity(intent);
//			Intent intent2 = new Intent(Intent.ACTION_VIEW);
//			intent2.setDataAndType(Uri.fromFile(new File(fileName)), "application/vnd.android.package-archive");
//			startActivity(intent2);
			
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void initLists() {
		Thread thread = new Thread(new Runnable() {
			//网络操作要在主线程之外进行
			public void run() {
				HttpURLConnection connection = null;
				try {
					String ur=getString(R.string.host)+"servlet/getAllApk";
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
						message.what=ALLApk;
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
