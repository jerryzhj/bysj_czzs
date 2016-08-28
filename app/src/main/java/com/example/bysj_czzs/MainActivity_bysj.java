package com.example.bysj_czzs;

import java.util.Date;
import java.util.List;

import service.AlertForToday;

import net.tsz.afinal.FinalDb;

import com.example.bysj_czzs3.R;
import com.example.bysj_part1.AddMissionActivity;
import com.example.bysj_part1.MainActivity_part1;
import com.example.bysj_part2.MainActivity_part2;
import com.example.bysj_part3.Activity_NetMissionGroup;
import com.example.bysj_part3.Activity_addMissionGroup;
import com.example.bysj_part3.Activity_showMissionGroup;
import com.example.bysj_part3.MainActivity_part3;
import com.example.bysj_part4.MainActivity_part4;
import com.squareup.picasso.Picasso;

import entity.Mission;
import entity.UserInfo;

import android.app.Activity;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.webkit.WebView.FindListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity_bysj extends Activity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks {

	
	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;

	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_bysj);
		Intent intent = new Intent(getApplicationContext(), AlertForToday.class);
		startService(intent);
		mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		//update the main content by replacing fragments
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager
				.beginTransaction()
				.replace(R.id.container,
						PlaceholderFragment.newInstance(position + 1)).commit();
	}

	public void onSectionAttached(int number) {
		Intent intent; 
		switch (number) {
		case 1:
			mTitle = getString(R.string.title_section0);
			break;
		case 2:
			mTitle = getString(R.string.title_section1);
			intent = new Intent(getApplicationContext(), MainActivity_part1.class);
			startActivity(intent);
			break;
		case 3:
			mTitle = getString(R.string.title_section2);
			intent = new Intent(getApplicationContext(), MainActivity_part2.class);
			startActivity(intent);
			break;
		case 4:
			mTitle = getString(R.string.title_section3);
			intent = new Intent(getApplicationContext(), MainActivity_part3.class);
			startActivity(intent);
			break;
		case 5:
			mTitle = getString(R.string.title_section4);
			intent = new Intent(getApplicationContext(), Activity_showMissionGroup.class);
			startActivity(intent);
			break;
		case 6:
			mTitle = getString(R.string.title_section5);
			intent = new Intent(getApplicationContext(), Activity_NetMissionGroup.class);
			startActivity(intent);
			break;
		case 7:
			finish();
			break;
		}
	}

	public void restoreActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			getMenuInflater().inflate(R.menu.main, menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Intent intent = new Intent(getApplicationContext(), MainActivity_part4.class);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		private TextView textView;
		private TextView textView2;
		private TextView textView3;
		private View rootView;
		private ImageView imageView;
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static PlaceholderFragment newInstance(int sectionNumber) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			
			rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			textView = (TextView) rootView.findViewById(R.id.textView_interFace);
			textView2 = (TextView) rootView.findViewById(R.id.textView_interface_Info);
			textView3 = (TextView) rootView.findViewById(R.id.textView_interface_mission_finished);
			imageView = (ImageView) rootView.findViewById(R.id.image_interface);
			init();
			
			return rootView;
		}

		private void init() {
			FinalDb db =FinalDb.create(getActivity());
			List<UserInfo> list = db.findAll(UserInfo.class);
			List<Mission> list2 = db.findAllByWhere(Mission.class,"isFinish=0");
			try {
				UserInfo info = list.get(list.size()-1);
				textView.setText("欢迎回来，"+info.getNickName());
				switch (info.getPicId()) {
				case 1:
					Picasso.with(getActivity()).load(R.drawable.girl).into(imageView);
					break;
				case 2:
					Picasso.with(getActivity()).load(R.drawable.kaliye).into(imageView);
					break;
				case 3:
					Picasso.with(getActivity()).load(R.drawable.sukura).into(imageView);
					break;
				case 4:
					Picasso.with(getActivity()).load(R.drawable.ling).into(imageView);
					break;
				default:
					Picasso.with(getActivity()).load(R.drawable.girl).into(imageView);
					break;
				}
			} catch (Exception e) {
				Picasso.with(getActivity()).load(R.drawable.girl).into(imageView);
				textView.setText("欢迎回来，主人");
				
			}
			int number =findMissionNumber(list2);
				if(number !=0){
					textView2.setText("您今天还有"+number+"个任务没有完成");
				}else{
					textView2.setText("您今天已经完成了所有任务");
				}
				int number_complete=0;
				List<Mission> list3 = db.findAllByWhere(Mission.class,"isFinish = 1");
				//List<Mission> list3 = db.findAll(Mission.class);
				Log.d("list", list3.toString());
				textView3.setText("您已经完成了"+list3.size()+"个任务");
		}

		private int findMissionNumber(List<Mission> list2) {
			int number = 0 ;
			for(int i = 0 ; i <list2.size();i++){
			Mission mission = list2.get(i);
			Date date = new Date();
			if(mission.getExcuteDate().getYear()>=date.getYear()
					&&mission.getExcuteDate().getMonth()>=date.getMonth()){
					if(mission.getExcuteDate().getDate()==date.getDate()){
						//加入今天执行的任务
						number++;
					}
				}
	        }
			return number;
		}

		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			
			((MainActivity_bysj) activity).onSectionAttached(getArguments().getInt(
					ARG_SECTION_NUMBER));
			//init();
		}
	}
	
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));
	}
	
}
