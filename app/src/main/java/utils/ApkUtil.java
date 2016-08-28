package utils;

import java.util.ArrayList;
import java.util.List;

import entity.Apk;


public class ApkUtil {
	public static List<Apk> getAllApk(String s) {
		List<Apk> list = new ArrayList<Apk>();
		int size=s.split("Apk").length-1;
		String[] name = getInfo(s,"name=");
		String[] url = getInfo(s,"url=");
		String[] content = getInfo(s, "content=");
		String[] action = getInfo(s,"action=");
		for(int i= 0 ; i <size;i++){
			Apk Apk = new Apk(i, name[i], url[i],content[i] , action[i]);
			list.add(Apk);
			System.out.println(Apk);
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
