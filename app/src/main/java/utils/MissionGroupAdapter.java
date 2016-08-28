package utils;

import java.util.List;

import com.example.bysj_czzs3.R;

import entity.Mission;
import entity.MissionGroup;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
/**
 * 
 * @author 张航舰
 *
 * @for(任务核心适配器)
 */
public class MissionGroupAdapter extends BaseExpandableListAdapter{
	private List<List<Mission>> data;//作为任务组的资源list。第一层是星期数，第二层是星期几。
	private Context context;
	private int categoryId;//第一层列表的id
	private int categoryItem_id;//第二层列表的id;
	private ImageView imageView;
	private EditText editText_content;
	private EditText editText_comment;
	private TextView textView_weeks;
	private TextView textView_days;
	private int groupPositions;
	private int childPositions;
	private Mission missions;
	private LinearLayout layout_missionGroup;
	
	//-------------------输入的变量----------------------------------


	public MissionGroupAdapter(List<List<Mission>> data2,
			Context applicationContext, int itemMissgroupParent,
			int itemMissiongroupChild) {
		data=data2;
		this.context=applicationContext;
		this.categoryId=itemMissgroupParent;
		this.categoryItem_id=itemMissiongroupChild;
	}

	//---------------------------自动生成的方法--------------------
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return data.get(groupPosition).get(childPosition);
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
		//设置子视图中显示为星期几
		textView_days = (TextView) convertView.findViewById(R.id.TextView_days);
		String days="";
		switch (childPosition) {
		case 0:
			days="一";
			break;
		case 1:days="二";
			break;
		case 2:
			days="三";
			break;
		case 3:days="四";
			break;
		case 4:
			days="五";
			break;
		case 5:days="六";
			break;
		default:days="日";
			break;
		}
		textView_days.setText("星期"+days);
		//获取得到的任务数据
	    missions =data.get(groupPosition).get(childPosition);
	    editText_comment=(EditText) convertView.findViewById(R.id.Edit_text_setcomment);
		editText_content=(EditText) convertView.findViewById(R.id.Edit_text_setcontent);
		layout_missionGroup=(LinearLayout) convertView.findViewById(R.id.LinearLayout_missiongroup);
		return convertView;
	}
	
	@Override
	public int getChildrenCount(int groupPosition) {
		return data.get(groupPosition).size();
	}

	@Override
	public Object getGroup(int groupPisition) {
		// TODO Auto-generated method stub
		return data.get(groupPisition);
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return data.size();
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
		textView_weeks = (TextView) convertView.findViewById(R.id.textView_missiongroup_Weeks);
		int weeks = groupPosition+1;
		textView_weeks.setText("第"+weeks+"周");
		return convertView;
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
	//当父列表收缩时
	@Override
	public void onGroupCollapsed(int groupPosition) {
		// TODO Auto-generated method stub
		super.onGroupCollapsed(groupPosition);
	}
}
