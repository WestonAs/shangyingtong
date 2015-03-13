package gnete.card.entity;

import java.util.Date;

public class WsMsgTypeCtrl extends WsMsgTypeCtrlKey {
	private String	status;
	private String	remark;
	private Date	insertTime;
	private Date	updateTime;
	private String	updateBy;

	/** 接口类型描述（非表字段） */
	private String	msgTypeDesc;

	/** 发卡机构名称（非表字段） */
	private String	branchName;

	/** 代理机构名称（非表字段） */
	private String	relatedIssName;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public String getMsgTypeDesc() {
		return msgTypeDesc;
	}

	public void setMsgTypeDesc(String msgTypeDesc) {
		this.msgTypeDesc = msgTypeDesc;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getRelatedIssName() {
		return relatedIssName;
	}

	public void setRelatedIssName(String relatedIssName) {
		this.relatedIssName = relatedIssName;
	}

}