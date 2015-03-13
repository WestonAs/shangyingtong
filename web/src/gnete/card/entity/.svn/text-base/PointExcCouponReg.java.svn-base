package gnete.card.entity;

import gnete.card.entity.state.RegisterState;

import java.math.BigDecimal;
import java.util.Date;

public class PointExcCouponReg {
    private Long pointExcCouponRegId;

    private String ptClass;

    private String ptName;

    private String couponClass;

    private String couponName;

    private String jinstType;

    private String jinstId;

    private BigDecimal ptValue;

    private String cardId;

    private String acctId;

    private String status;

    private String branchCode;

    private String updateBy;

    private Date updateTime;

    private String remark;
    
    private String ptExchgRuleId;
    
    private BigDecimal couponAmt;
    
    private BigDecimal excRate;
    
    private String couponClassName;
    
    public Long getPointExcCouponRegId() {
        return pointExcCouponRegId;
    }

    public void setPointExcCouponRegId(Long pointExcCouponRegId) {
        this.pointExcCouponRegId = pointExcCouponRegId;
    }

    public String getPtClass() {
        return ptClass;
    }

    public void setPtClass(String ptClass) {
        this.ptClass = ptClass;
    }

    public String getPtName() {
        return ptName;
    }

    public void setPtName(String ptName) {
        this.ptName = ptName;
    }

    public String getCouponClass() {
        return couponClass;
    }

    public void setCouponClass(String couponClass) {
        this.couponClass = couponClass;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public String getJinstType() {
        return jinstType;
    }

    public void setJinstType(String jinstType) {
        this.jinstType = jinstType;
    }

    public String getJinstId() {
        return jinstId;
    }

    public void setJinstId(String jinstId) {
        this.jinstId = jinstId;
    }

    public BigDecimal getPtValue() {
        return ptValue;
    }

    public void setPtValue(BigDecimal ptValue) {
        this.ptValue = ptValue;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getAcctId() {
        return acctId;
    }

    public void setAcctId(String acctId) {
        this.acctId = acctId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

	public BigDecimal getCouponAmt() {
		return couponAmt;
	}

	public void setCouponAmt(BigDecimal couponAmt) {
		this.couponAmt = couponAmt;
	}

	public BigDecimal getExcRate() {
		return excRate;
	}

	public void setExcRate(BigDecimal excRate) {
		this.excRate = excRate;
	}
	
	public String getStatusName(){
		return RegisterState.ALL.get(this.status) == null ? "" : RegisterState.valueOf(this.status).getName();
	}

	public String getPtExchgRuleId() {
		return ptExchgRuleId;
	}

	public void setPtExchgRuleId(String ptExchgRuleId) {
		this.ptExchgRuleId = ptExchgRuleId;
	}

	public String getCouponClassName() {
		return couponClassName;
	}

	public void setCouponClassName(String couponClassName) {
		this.couponClassName = couponClassName;
	}
}