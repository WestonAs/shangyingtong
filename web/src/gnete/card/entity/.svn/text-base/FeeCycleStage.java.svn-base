package gnete.card.entity;

import gnete.card.entity.type.FeeCycleType;
import gnete.card.entity.type.FeePrincipleType;

import java.util.Date;

public class FeeCycleStage extends FeeCycleStageKey {
    private String feeCycleType;

    private String feeBeginDate;

    private String feeEndDate;

    private String adjustBeginDate;

    private String adjustEndDate;

    private String updateBy;

    private Date updateTime;

    private String feePrinciple;

    public String getFeeCycleType() {
        return feeCycleType;
    }

    public void setFeeCycleType(String feeCycleType) {
        this.feeCycleType = feeCycleType;
    }

    public String getFeeBeginDate() {
        return feeBeginDate;
    }

    public void setFeeBeginDate(String feeBeginDate) {
        this.feeBeginDate = feeBeginDate;
    }

    public String getFeeEndDate() {
        return feeEndDate;
    }

    public void setFeeEndDate(String feeEndDate) {
        this.feeEndDate = feeEndDate;
    }

    public String getAdjustBeginDate() {
        return adjustBeginDate;
    }

    public void setAdjustBeginDate(String adjustBeginDate) {
        this.adjustBeginDate = adjustBeginDate;
    }

    public String getAdjustEndDate() {
        return adjustEndDate;
    }

    public void setAdjustEndDate(String adjustEndDate) {
        this.adjustEndDate = adjustEndDate;
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

    public String getFeePrinciple() {
        return feePrinciple;
    }

    public void setFeePrinciple(String feePrinciple) {
        this.feePrinciple = feePrinciple;
    }
    
    public String getFeeCycleTypeName() {
		return FeeCycleType.ALL.get(this.feeCycleType) == null ? "" : FeeCycleType.valueOf(this.feeCycleType).getName();
	}
    
    public String getFeePrincipleName() {
		return FeePrincipleType.ALL.get(this.feePrinciple) == null ? "" : FeePrincipleType.valueOf(this.feePrinciple).getName();
	}
}