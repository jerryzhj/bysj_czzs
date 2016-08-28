package entity;

import java.util.List;

import net.tsz.afinal.annotation.sqlite.Table;
@Table(name="bysj_userInfo")
public class UserInfo {
	private int id;
	private String nickName;
	private String Email;
	private int PicId;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public int getPicId() {
		return PicId;
	}
	public void setPicId(int picId) {
		PicId = picId;
	}
	public UserInfo(int id, String nickName, String email, int picId) {
		super();
		this.id = id;
		this.nickName = nickName;
		Email = email;
		PicId = picId;
	}
	@Override
	public String toString() {
		return "UserInfo [id=" + id + ", nickName=" + nickName + ", Email="
				+ Email + ", PicId=" + PicId + "]";
	}
	public UserInfo() {
		super();
	}
	
}
