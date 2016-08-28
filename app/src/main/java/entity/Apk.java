package entity;

public class Apk {
	private int id;
	private String name;
	private String url;
	private String content;
	private String action;
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getaction() {
		return action;
	}
	public void setaction(String action) {
		this.action = action;
	}
	public Apk(int id, String name, String url, String content, String action) {
		super();
		this.id = id;
		this.name = name;
		this.url = url;
		this.content = content;
		this.action = action;
	}
	@Override
	public String toString() {
		return "Apk [id=" + id + ", name=" + name + ", url=" + url
				+ ", content=" + content + ", action=" + action + "]";
	}
	
}
