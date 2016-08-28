package service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.example.bysj_czzs.MainActivity_bysj;
import com.example.bysj_czzs3.R;

import net.tsz.afinal.FinalDb;


import entity.Mission;

import android.R.integer;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

public class AlertForToday extends Service{

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
			doNotify();
				
			}
		}, 1000,3000);
	
	}
	private void doNotify() {
			NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		Intent intent = new Intent();
		//PendingIntent pendingIntent=PendingIntent.getService(getApplicationContext(), 1, intent, 0);
		Notification myNotify = new Notification(); 
		
		//自定义的notification因为android API的限制依然需要设置icon等属性
        myNotify.icon = R.drawable.ic_launcher;  
        myNotify.tickerText = "TickerText:您有新短消息，请注意查收！";  
        myNotify.when = System.currentTimeMillis();  
        myNotify.flags = Notification.FLAG_NO_CLEAR;// 不能够自动清除  
       
        //使用自定义的notification
        RemoteViews rv = new RemoteViews(getPackageName(),  
                R.layout.my_notification);  
        String notifyText = getText();
        rv.setTextViewText(R.id.text_content, notifyText);  
        myNotify.contentView = rv; 
        
        //设置点击的intent
//        Intent notificationIntent = new Intent(this,MainActivity.class);
//		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
//		notificationIntent, 0);
        Intent intent3 = new Intent(this,MainActivity_bysj.class);  
        PendingIntent contentIntent = PendingIntent.getActivity(this, 1,  
                intent3, 1);  
        myNotify.contentIntent = contentIntent;  
        manager.notify(1, myNotify);  
	}
	private String getText() {
		FinalDb db =FinalDb.create(getApplicationContext());
		List<Mission> listfortoday = db.findAllByWhere(Mission.class,"isFinish = 0");
		Date date = new Date();
		int i=0;
		for (Iterator iterator = listfortoday.iterator(); iterator.hasNext();) {
			Mission mission = (Mission) iterator.next();
			Log.d("missions", mission.toString());
			if(mission.getExcuteDate().getYear()==date.getYear()
					&&mission.getExcuteDate().getMonth()==date.getMonth()){
					if(mission.getExcuteDate().getDate()==date.getDate()){
						//加入今天执行的任务
//						Log.d("mission", mission.toString());
//						if(mission.getIsFinish()==null||!mission.getIsFinish().equals("null")){
//						//判断任务是否已经完成
							Log.d("poste",mission.getIsFinish()+"aa");
							i++;
//						}
					}
			}
		}if(i!=0){
			String text="您还有"+i+"个任务没有完成";
			return text;
		}else{
			String text="您已经完成了所有任务";
			return text;
		}
		
	}
}
