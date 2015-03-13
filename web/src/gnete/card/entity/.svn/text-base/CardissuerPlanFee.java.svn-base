package gnete.card.entity;

import gnete.card.entity.state.CommonState;
import gnete.card.entity.type.ChargeType;

import java.math.BigDecimal;
import java.util.Date;

public class CardissuerPlanFee {
    private String issId;

    private String planId;

    private String chargeType;

    private BigDecimal chargeAmt;

    private BigDecimal defauleAmt;

    private BigDecimal customAmt;

    private String status;

    private String updateBy;

    private Date updateTime;

    public String getIssId() {
        return issId;
    }

    public void setIssId(String issId) {
        this.issId = issId;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
    
    /**
     * 状态名
     * 
     * @return
     */
    public String getStatusName() {
        return CommonState.ALL.get(status) == null ? "" : CommonState.valueOf(status).getName();
    }
    
    /**
     * 单机产品套餐收费标准名
     * @return
     */
    public String getChargeTypeName() {
    	return ChargeType.ALL.get(chargeType) == null ? "" : ChargeType.valueOf(chargeType).getName();
    }
}