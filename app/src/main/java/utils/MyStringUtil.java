package utils;

import java.util.ArrayList;
import java.util.List;

import entity.MissionGroup;
import entity.MissionsForGroup;

public class MyStringUtil {
	public static void main(String[] args) {
		getAllMissions("createDateaa[MissionsForGroup [id=1, MissionGroupId=1, content=唱个歌, comment=发过火, weeks=1, days=1], MissionsForGroup [id=2, MissionGroupId=1, content=唱个歌, comment=唱歌呵呵, weeks=1, days=2]]",1);
	}
	public static List<MissionsForGroup> getAllMissions(String s,int id) {
		List<MissionsForGroup> list = new ArrayList<MissionsForGroup>();
		int size=s.split("MissionsForGroup").length-1;
		String[] content = getInfo(s,"content=");
		String[] comment = getInfo(s,"comment=");
		String[] weeks = getInfo(s, "weeks=");
		String[] days = getInfo(s, "days=");
		for(int i= 0 ; i <size;i++){
			MissionsForGroup forGroup = new MissionsForGroup();
			forGroup.setContent(content[i]);
			forGroup.setComment(comment[i]);
			forGroup.setWeeks(Integer.parseInt(weeks[i]));
			forGroup.setDays(Integer.parseInt(days[i].split("]")[0]));
			forGroup.setMissionGroupId(id);
			list.add(forGroup);
		}
		System.out.println(list.toString());
		return list;
	}
	public static String[] getInfo(String s, String string) {
		String[] data = new String[100];
		String[] c = s.split(string);
		String[] d = new String[c.length-1] ;
		for(int i = 1 ; i <c.length;i++){
			String k=c[i].split(",")[0];
			d[i-1]=k;
			data[i-1]=k;
		}
		return data;
	}
	
	public static MissionGroup getMissionGroup(String s){
		MissionGroup group = new MissionGroup();
		List<MissionsForGroup> list = new ArrayList<MissionsForGroup>();
		int size=s.split("MissionGroup").length-1;
		String[] url = getInfo(s,"pictureUrl=");
		String[] comment = getInfo(s,"comment=");
		String[] content = getInfo(s, "content=");
		for(int i= 0 ; i <size;i++){
			group.setPictureUrl(url[0]);
			group.setComment(comment[0]);
			group.setContent(content[0]);
		}
		return group;
	}
	public static List<MissionGroup> getMissionGroups(String s){
		List<MissionGroup> list = new ArrayList<MissionGroup>();
		int size=s.split("MissionGroup").length-1;
		String[] id = getInfo(s, "id=");
		String[] url = getInfo(s,"pictureUrl=");
		String[] comment = getInfo(s,"comment=");
		String[] content = getInfo(s, "content=");
		for(int i= 0 ; i <size;i++){
			MissionGroup group = new MissionGroup();
			group.setId(Integer.parseInt(id[i]));
			group.setPictureUrl(url[i]);
			group.setComment(comment[i]);
			group.setContent(content[i]);
			list.add(group);
		}
		return list;
	}
}
