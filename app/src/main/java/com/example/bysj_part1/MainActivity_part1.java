package com.example.bysj_part1;

import com.example.bysj_czzs3.R;

import service.AlertForToday;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.FeatureInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity_part1 extends Activity {

	private Fragment_misson fragment_misson;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_part1);
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragment_misson = new Fragment_misson();
		fragmentTransaction.add(R.id.FrameLayout1,fragment_misson);
		fragmentTransaction.commit();
		
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
			Intent intent = new Intent(getApplicationContext(), AlertForToday.class);
			startService(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		setContentView(R.layout.activity_main_part1);
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragment_misson = new Fragment_misson();
		fragmentTransaction.add(R.id.FrameLayout1,fragment_misson);
		fragmentTransaction.commit();
	}
}
