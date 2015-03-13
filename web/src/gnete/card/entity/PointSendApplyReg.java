package gnete.card.entity;

import gnete.card.entity.state.PointSendApplyRegState;

import java.math.BigDecimal;
import java.util.Date;

public class PointSendApplyReg {
    private Long applyId;
    private String cardIssuer;
    private String ptClass;
    private String beginDate;
    private String endDate;
    private BigDecimal thresholdValue;
    private BigDecimal sendPoint;
    private String status;
    private String remark;
    /** 后台处理结果描述 */
    private String memo;
    private String entryUser;
    private Date updateTime;
    
    //---
    private String ptClassName;
    
    
    /** 获得状态字段对应的名称 */
    public String getStatusName(){
		return PointSendApplyRegState.ALL.get(this.status) == null ? ""
				: PointSendApplyRegState.valueOf(this.status).getName();
    }
    
    public Long getApplyId() {
        return applyId;
    }

    public void setApplyId(Long applyId) {
        this.applyId = applyId;
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

    public BigDecimal getThresholdValue() {
        return thresholdValue;
    }

    public void setThresholdValue(BigDecimal thresholdValue) {
        this.thresholdValue = thresholdValue;
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

    public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getEntryUser() {
        return entryUser;
    }

    public void setEntryUser(String entryUser) {
        this.entryUser = entryUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

	public String getPtClassName() {
		return ptClassName;
	}

	public void setPtClassName(String ptClassName) {
		this.ptClassName = ptClassName;
	}
    
    
}