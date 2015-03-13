package gnete.card.entity;

import gnete.card.entity.state.AwdState;

import java.util.Date;

public class DrawInfoReg extends DrawInfoRegKey {
	private String status;

	private String drawName;

	private String name;

	private String cardIssuer;

	private String updateBy;

	private Date updateTime;

	private String mobilePhone;

	private String remark;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDrawName() {
		return drawName;
	}

	public void setDrawName(String drawName) {
		this.drawName = drawName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCardIssuer() {
		return cardIssuer;
	}

	public void setCardIssuer(String cardIssuer) {
		this.cardIssuer = cardIssuer;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	//以下为附加方法
	/** 用户抽奖状态 */
	public String getStatusName() {
		return status != null ? AwdState.valueOf(status).getName():"";
	}
}