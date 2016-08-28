package utils;

import java.util.ArrayList;
import java.util.List;

import entity.Mood;

public class Util4bysj_part2 {
	public static List<Mood> getAllMood(String s) {
		List<Mood> list = new ArrayList<Mood>();
		int size=s.split("Mood").length-1;
		String[] content = getInfo(s,"content=");
		String[] createDate = getInfo(s,"createDate=");
		String[] fileDir = getInfo(s, "fileDir=");

		for(int i= 0 ; i <size;i++){
			Mood mood = new Mood();
			mood.setContnet(content[i]);
			mood.setCreatedate(createDate[i]);
			mood.setUrl(fileDir[i]);
			list.add(mood);
			System.out.println(mood);
		}
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
}
