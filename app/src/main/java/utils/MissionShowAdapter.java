package utils;

import java.util.List;

import com.example.bysj_czzs3.R;


import entity.MissionGroup;
import entity.MissionsForGroup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class MissionShowAdapter extends BaseAdapter{
	private Context context;
	private List<MissionsForGroup> list_missions;
	
	public MissionShowAdapter(Context context, List<MissionsForGroup> list,
			MissionGroup missionGroup) {
		super();
		this.context = context;
		this.list_missions = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list_missions.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list_missions.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView==null){
			LayoutInflater listContainer = LayoutInflater.from(context); //创建视图容器工厂并设置上下文

	        convertView = listContainer.inflate(R.layout.activity_listview_item, null);
		}
		return convertView;
	}
}
