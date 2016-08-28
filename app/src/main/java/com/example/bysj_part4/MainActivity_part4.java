package com.example.bysj_part4;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import com.example.bysj_czzs3.R;

import entity.UserInfo;
import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.FinalDb;
import net.tsz.afinal.annotation.view.ViewInject;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity_part4 extends FinalActivity {
	private static final int SUCCESS=1239;  
	@ViewInject(id=R.id.editText1)
	private EditText editText_nickName;
	@ViewInject(id=R.id.editText2)
	private EditText editText_Email;
	@ViewInject(id=R.id.textView_img)
	private TextView textView_img;
	@ViewInject(id=R.id.imageView1,click="setImage")
	private ImageView imageView1;
	@ViewInject(id=R.id.imageView2,click="setImage")
	private ImageView imageView2;
	@ViewInject(id=R.id.imageView3,click="setImage")
	private ImageView imageView3;
	@ViewInject(id=R.id.imageView4,click="setImage")
	private ImageView imageView4;
	@ViewInject(id=R.id.button_submit_setting,click="doSubmit")
	private Button button;
	@ViewInject(id=R.id.button_pat5_upLoad,click="doUpLoad")
	private Button button_upLoad;
	private int imageId;
	private String text;
	private Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			if(msg.what==SUCCESS){
				
			}
		};
	};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_part4);
		imageId=R.drawable.ling;
		imageView1.setImageResource(R.drawable.girl);
		imageView2.setImageResource(R.drawable.kaliye);
		imageView3.setImageResource(R.drawable.sukura);
		imageView4.setImageResource(R.drawable.ling);
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
			Intent intent = new Intent(getApplicationContext(), Activity_ShowEffect.class);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	public void setImage(View v){
		switch (v.getId()) {
		case R.id.imageView1:
			textView_img.setText("您选择的是第一张图片");
			imageId = 1;
			break;
		case R.id.imageView2:
			textView_img.setText("您选择的是第二张图片");
			imageId = 2;
			break;
		case R.id.imageView3:
			textView_img.setText("您选择的是第三张图片");
			imageId = 3;
			break;
		case R.id.imageView4:
			textView_img.setText("您选择的是第四张图片");
			imageId = 4;
		default:
			break;
		}
	}
	
	public void doSubmit(View v){
		String name = editText_nickName.getText().toString();
		String email = editText_Email.getText().toString();
		UserInfo info = new UserInfo(1, name, email, imageId);
		FinalDb db=FinalDb.create(getApplicationContext());
		db.save(info);
		Log.d("aa", info.toString());
		Toast.makeText(getApplicationContext(), "添加成功", Toast.LENGTH_LONG).show();
		List<UserInfo>list =db.findAll(UserInfo.class);
		Log.d("list", list.toString());
	}
	
	public void doUpLoad(View v){
		Thread thread = new Thread(new Runnable() {
			//网络操作要在主线程之外进行
			public void run() {
				HttpURLConnection connection = null;
				try {
					String ur=getString(R.string.host)+"servlet/DoLogin";
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
						message.what=SUCCESS;
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
