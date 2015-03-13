package gnete.card.entity;

import gnete.card.entity.state.CommonState;
import gnete.card.entity.type.ChargeType;

import java.math.BigDecimal;
import java.util.Date;

public class PlanDef {
	
    private String planId;

    private String planName;

    private String terType;

    private String effDate;

    private String expirDate;

    private String status;

    private String authority;

    private String remark;

    private String branchCode;

    private String chargeType;

    private BigDecimal chargeAmt;

    private BigDecimal defauleAmt;

    private BigDecimal customAmt;

    private String updateBy;

    private Date updateTime;
    
    // 卡类型模板号
    private String cardSubclassTemp;

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getTerType() {
        return terType;
    }

    public void setTerType(String terType) {
        this.terType = terType;
    }

    public String getEffDate() {
        return effDate;
    }

    public void setEffDate(String effDate) {
        this.effDate = effDate;
    }

    public String getExpirDate() {
        return expirDate;
    }

    public void setExpirDate(String expirDate) {
        this.expirDate = expirDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getChargeType() {
        return chargeType;
    }

    public void setChargeType(String chargeType) {
        this.chargeType = chargeType;
    }

    public BigDecimal getChargeAmt() {
        return chargeAmt;
    }

    public void setChargeAmt(BigDecimal chargeAmt) {
        this.chargeAmt = chargeAmt;
    }

    public BigDecimal getDefauleAmt() {
        return defauleAmt;
    }

    public void setDefauleAmt(BigDecimal defauleAmt) {
        this.defauleAmt = defauleAmt;
    }

    public BigDecimal getCustomAmt() {
        return customAmt;
    }

    public void setCustomAmt(BigDecimal customAmt) {
        this.customAmt = customAmt;
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
    
    public String getCardSubclassTemp() {
    	return cardSubclassTemp;
    }
    
    public void setCardSubclassTemp(String cardSubclassTemp) {
    	this.cardSubclassTemp = cardSubclassTemp;
    }
    
    /**
	 * 状态名
	 * @return
	 */
	public String getStatusName() {
		return CommonState.ALL.get(this.status) == null ? "" : CommonState
				.valueOf(this.status).getName();
	}

	/**
	 * 收费标准名
	 * @return
	 */
	public String getChargeTypeName() {
		return ChargeType.ALL.get(this.chargeType) == null ? "" : ChargeType
				.valueOf(this.chargeType).getName();
	}
}