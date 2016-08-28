package entity;

import java.util.Date;
import net.tsz.afinal.annotation.sqlite.*;
@Table(name="bysj_MyMission")
public class Mission {
	private int id;
	private String content;
	private Date Createdate;
	private String comment;
	private Date excuteDate;
	private Boolean isFinish;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreatedate() {
		return Createdate;
	}
	public void setCreatedate(Date createdate) {
		Createdate = createdate;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public Date getExcuteDate() {
		return excuteDate;
	}
	public void setExcuteDate(Date excuteDate) {
		this.excuteDate = excuteDate;
	}
	public Boolean getIsFinish() {
		return isFinish;
	}
	public void setIsFinish(Boolean isFinish) {
		this.isFinish = isFinish;
	}
	@Override
	public String toString() {
		return "Mission [id=" + id + ", content=" + content + ", Createdate="
				+ Createdate + ", comment=" + comment + ", excuteDate="
				+ excuteDate + ", isFinish=" + isFinish + "]";
	}
	
	
}
