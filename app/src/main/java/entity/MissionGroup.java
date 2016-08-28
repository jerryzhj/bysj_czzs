package entity;

import java.io.Serializable;

public class MissionGroup implements Serializable{
	private int id;
	private String name;
	private String pictureUrl;
	private String comment;
	private String content;
	private Float stars;
	@Override
	public String toString() {
		return "MissionGroup [id=" + id + ", name=" + name + ", pictureUrl="
				+ pictureUrl + ", comment=" + comment + ", content=" + content
				+ ", stars=" + stars +"]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPictureUrl() {
		return pictureUrl;
	}
	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Float getStars() {
		return stars;
	}
	public void setStars(Float stars) {
		this.stars = stars;
	}
	public MissionGroup(String pictureUrl, String comment,
			String content) {
		super();
		this.pictureUrl = pictureUrl;
		this.comment = comment;
		this.content = content;
	}
	public MissionGroup() {
		super();
	}
	public MissionGroup(int id, String name, String pictureUrl, String comment,
			String content, Float stars) {
		super();
		this.id = id;
		this.name = name;
		this.pictureUrl = pictureUrl;
		this.comment = comment;
		this.content = content;
		this.stars = stars;
	}
	
	
}
