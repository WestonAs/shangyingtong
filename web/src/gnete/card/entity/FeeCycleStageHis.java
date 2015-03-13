package gnete.card.entity;

import java.util.Date;

public class FeeCycleStageHis {
    private Long id;

    private Integer feeRuleId;

    private String feeDate;

    private String feeCycleType;

    private String feeBeginDate;

    private String feeEndDate;

    private String adjustBeginDate;

    private String adjustEndDate;

    private String feePrinciple;

    private String updateBy;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getFeeRuleId() {
        return feeRuleId;
    }

    public void setFeeRuleId(Integer feeRuleId) {
        this.feeRuleId = feeRuleId;
    }

    public String getFeeDate() {
        return feeDate;
    }

    public void setFeeDate(String feeDate) {
        this.feeDate = feeDate;
    }

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

    public String getFeePrinciple() {
        return feePrinciple;
    }

    public void setFeePrinciple(String feePrinciple) {
        this.feePrinciple = feePrinciple;
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
}