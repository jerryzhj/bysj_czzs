package entity;

public class MissionsForGroup {
	private int id;
	private int MissionGroupId;
	private String content;
	private String comment;
	private int weeks;
	private int days;
	@Override
	public String toString() {
		return "MissionsForGroup [id=" + id + ", MissionGroupId="
				+ MissionGroupId + ", content=" + content + ", comment="
				+ comment + ", weeks=" + weeks + ", days=" + days + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMissionGroupId() {
		return MissionGroupId;
	}
	public void setMissionGroupId(int missionGroupId) {
		MissionGroupId = missionGroupId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public int getWeeks() {
		return weeks;
	}
	public void setWeeks(int weeks) {
		this.weeks = weeks;
	}
	public int getDays() {
		return days;
	}
	public void setDays(int days) {
		this.days = days;
	}
	public MissionsForGroup(int missionGroupId, String content, String comment,
			int weeks, int days) {
		super();
		MissionGroupId = missionGroupId;
		this.content = content;
		this.comment = comment;
		this.weeks = weeks;
		this.days = days;
	}
	public MissionsForGroup(int id, int missionGroupId, String content,
			String comment, int weeks, int days) {
		super();
		this.id = id;
		MissionGroupId = missionGroupId;
		this.content = content;
		this.comment = comment;
		this.weeks = weeks;
		this.days = days;
	}
	public MissionsForGroup() {
		super();
	}
	
}
