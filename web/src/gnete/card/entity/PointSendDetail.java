package gnete.card.entity;

import gnete.card.entity.state.PointSendDetailState;

import java.math.BigDecimal;
import java.util.Date;

public class PointSendDetail extends PointSendDetailKey {
	private String cardId;

	private String cardIssuer;

	private String ptClass;

	private String beginDate;

	private String endDate;

	private BigDecimal curAccuPt;

	private BigDecimal sendPoint;

	private String status;

	private String remark;

	private String updateUser;

	private Date updateTime;

    /** 获得状态字段对应的名称 */
    public String getStatusName(){
		return PointSendDetailState.ALL.get(this.status) == null ? ""
				: PointSendDetailState.valueOf(this.status).getName();
    }
	
	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getCardIssuer() {
		return cardIssuer;
	}

	public void setCardIssuer(String cardIssuer) {
		this.cardIssuer = cardIssuer;
	}

	public String getPtClass() {
		return ptClass;
	}

	public void setPtClass(String ptClass) {
		this.ptClass = ptClass;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public BigDecimal getCurAccuPt() {
		return curAccuPt;
	}

	public void setCurAccuPt(BigDecimal curAccuPt) {
		this.curAccuPt = curAccuPt;
	}

	public BigDecimal getSendPoint() {
		return sendPoint;
	}

	public void setSendPoint(BigDecimal sendPoint) {
		this.sendPoint = sendPoint;
	}

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

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}