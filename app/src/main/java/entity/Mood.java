package entity;

import java.util.Date;

import net.tsz.afinal.annotation.sqlite.Table;
@Table(name="bysj_MyMood")
public class Mood {
	private int id;
	private String contnet;
	private String createdate;
	private String url;
	private int isUpLoad;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContnet() {
		return contnet;
	}
	public void setContnet(String contnet) {
		this.contnet = contnet;
	}
	public String getCreatedate() {
		return createdate;
	}
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getIsUpLoad() {
		return isUpLoad;
	}
	public void setIsUpLoad(int isUpLoad) {
		this.isUpLoad = isUpLoad;
	}
	@Override
	public String toString() {
		return "Mood [id=" + id + ", contnet=" + contnet + ", createdate="
				+ createdate + ", url=" + url + ", isUpLoad=" + isUpLoad + "]";
	}
	
	
}
