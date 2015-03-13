package gnete.card.entity;

import gnete.card.entity.flag.ShowFlag;

import java.util.Date;

public class PublishNotice {
	private String id;

	private String content;

	private String pubUser;

	private Date pubTime;

	private String title;

	private String showFlag;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPubUser() {
		return pubUser;
	}

	public void setPubUser(String pubUser) {
		this.pubUser = pubUser;
	}

	public Date getPubTime() {
		return pubTime;
	}

	public void setPubTime(Date pubTime) {
		this.pubTime = pubTime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getShowFlag() {
		return showFlag;
	}

	public void setShowFlag(String showFlag) {
		this.showFlag = showFlag;
	}

	/**
	 * 显示标志
	 * @return
	 */
	public String getShowFlagName() {
		return ShowFlag.ALL.get(this.showFlag) == null ? "" : ShowFlag.valueOf(
				this.showFlag).getName();
	}
}