package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.example.bysj_czzs3.R;
import com.example.bysj_part1.AddMissionActivity;

import entity.Mission;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * 
 * @author 张航舰
 *
 * @for(任务核心适配器)
 */
public class MissionAdapter extends BaseExpandableListAdapter{
	//private List<Mission> firstData;
	private List<Mission> missionForToday;//今日任务
	private List<Mission> missionForTommorrow;//明日任务
	private List<Mission> missionForSoon;//即将需要完成的任务
	private List<Mission> missionForFarLater;//以后再说
	private String[] category = new String[] {"今日任务", "明日任务", "本月要完成的任务","很久以后的任务"}; //
	private Context context;
	private int categoryId;//第一层列表的id
	private int categoryItem_id;//第二层列表的id;
	private ImageView imageView;
	private TextView textView;
	private TextView textView_item;
	
	//-------------------输入的变量----------------------------------
	public MissionAdapter(List<Mission> missionForToday,
			List<Mission> missionForTommorrow, List<Mission> missionForSoon,
			List<Mission> missionForFarLater) {
		super();
		this.missionForToday = missionForToday;
		this.missionForTommorrow = missionForTommorrow;
		this.missionForSoon = missionForSoon;
		this.missionForFarLater = missionForFarLater;
		
	}
	
	


	public MissionAdapter(List<Mission> missionForToday,
			List<Mission> missionForTommorrow, List<Mission> missionForSoon,
			List<Mission> missionForFarLater, Context context, int categoryId,
			int categoryItem_id) {
		super();
		this.missionForToday = missionForToday;
		this.missionForTommorrow = missionForTommorrow;
		this.missionForSoon = missionForSoon;
		this.missionForFarLater = missionForFarLater;
		this.context = context;
		this.categoryId = categoryId;
		this.categoryItem_id = categoryItem_id;
	}




	@Override
	public String toString() {
		return "missionAdapter [missionForToday=" + missionForToday
				+ ", missionForTommorrow=" + missionForTommorrow
				+ ", missionForSoon=" + missionForSoon
				+ ", missionForFarLater=" + missionForFarLater + ", category="
				+ Arrays.toString(category) + "]";
	}
//---------------------------自动生成的方法--------------------
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		switch (groupPosition) {
		case 0:
			return missionForToday.get(childPosition);
		case 1:
			return missionForTommorrow.get(childPosition);
		case 2:
			return missionForSoon.get(childPosition);
		case 3:
			return missionForFarLater.get(childPosition);
		default:
			break;
		}
		return null;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}
	//得到子视图
	@Override
	public View getChildView(int groupPosition, int childPosition,  
            boolean isLastChild, View convertView, ViewGroup parent) {  
		convertView = View.inflate(context,categoryItem_id, null);
		textView_item =(TextView) convertView.findViewById(R.id.textView_missionItem);
		List<Mission> items = new ArrayList<Mission>();
		switch (groupPosition) {
		case 0:
			items = missionForToday;
			break;
		case 1:
			items = missionForTommorrow;
			break;
		case 2:
			items = missionForSoon;
			break;
		case 3:
			items =missionForFarLater;
			break;
		default:
			break;
		}
		String itemDetail=items.get(childPosition).getContent();
		Log.d("item", itemDetail+"aa");
		Boolean isFinish = items.get(childPosition).getIsFinish();
		Log.d("aa", isFinish+"aa");
		if(isFinish==true){
			convertView.setBackgroundResource(R.color.gray_cc);
		}
		textView_item.setText(itemDetail);
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		switch (groupPosition) {
		case 0:
			return missionForToday.size();
		case 1:
			return missionForTommorrow.size();
		case 2:
			return missionForSoon.size();
		case 3:
			return missionForFarLater.size();
		default:
			break;
		}
		return 0;
	}

	@Override
	public Object getGroup(int groupPisition) {
		// TODO Auto-generated method stub
		return category[groupPisition];
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return category.length;
	}

	@Override
	public long getGroupId(int groupId) {
		// TODO Auto-generated method stub
		return groupId;
	}
	//得到父亲视图
	@Override
	 public View getGroupView(int groupPosition, boolean isExpanded,  
             View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		convertView = View.inflate(context,categoryId, null);
		imageView =(ImageView) convertView.findViewById(R.id.addMissions);
		textView = (TextView) convertView.findViewById(R.id.textView_fenlei);
		textView.setText(category[groupPosition]);
		Log.d("aa", category[groupPosition]);
		setOnclickLister();
		return convertView;
	}

	private void setOnclickLister() {
		imageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(context, AddMissionActivity.class);
				context.startActivity(intent);	
			}
		});
	}




	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isChildSelectable(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return true;
	}

}
