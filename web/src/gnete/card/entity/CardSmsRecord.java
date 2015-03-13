package gnete.card.entity;

import java.util.Date;

public class CardSmsRecord {
	private Long	id;

	private String	msgId;

	private String	issNo;

	private String	smsServiceType;

	private String	mobile;

	private String	smsSendContent;

	private String	smsRecContent;

	private Date	sendTime;

	private Date	recTime;

	private String	flag;

	private String	comeFrom;

	private Date	insertTime;

	private String	updateBy;

	public CardSmsRecord() {
		super();
	}

	public CardSmsRecord(String issNo, String smsServiceType, String mobile, String smsSendContent,
			String flag, String comeFrom, Date insertTime, String updateBy) {
		super();
		this.issNo = issNo;
		this.smsServiceType = smsServiceType;
		this.mobile = mobile;
		this.smsSendContent = smsSendContent;
		this.flag = flag;
		this.comeFrom = comeFrom;
		this.insertTime = insertTime;
		this.updateBy = updateBy;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getIssNo() {
		return issNo;
	}

	public void setIssNo(String issNo) {
		this.issNo = issNo;
	}

	public String getSmsServiceType() {
		return smsServiceType;
	}

	public void setSmsServiceType(String smsServiceType) {
		this.smsServiceType = smsServiceType;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getSmsSendContent() {
		return smsSendContent;
	}

	public void setSmsSendContent(String smsSendContent) {
		this.smsSendContent = smsSendContent;
	}

	public String getSmsRecContent() {
		return smsRecContent;
	}

	public void setSmsRecContent(String smsRecContent) {
		this.smsRecContent = smsRecContent;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public Date getRecTime() {
		return recTime;
	}

	public void setRecTime(Date recTime) {
		this.recTime = recTime;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getComeFrom() {
		return comeFrom;
	}

	public void setComeFrom(String comeFrom) {
		this.comeFrom = comeFrom;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
}