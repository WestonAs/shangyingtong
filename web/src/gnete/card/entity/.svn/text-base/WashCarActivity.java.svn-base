package gnete.card.entity;

import gnete.card.entity.type.WashCarCycleType;
import gnete.card.entity.type.WashTherInvalIdType;

import java.util.Date;

public class WashCarActivity {
    private Long activityId;

    private String cardIssuer;

    private String merchId;

    private String activityName;

    private String washCarCycle;

    private Long totalNum;

    private Long limitMonthNum;

    private String whetherInvalid;

    private Date updateTime;

    private String updateUser;

    private String remark;

    private Date insertTime;

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public String getCardIssuer() {
        return cardIssuer;
    }

    public void setCardIssuer(String cardIssuer) {
        this.cardIssuer = cardIssuer;
    }

    public String getMerchId() {
        return merchId;
    }

    public void setMerchId(String merchId) {
        this.merchId = merchId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getWashCarCycle() {
        return washCarCycle;
    }

    public void setWashCarCycle(String washCarCycle) {
        this.washCarCycle = washCarCycle;
    }

    public Long getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Long totalNum) {
        this.totalNum = totalNum;
    }

    public Long getLimitMonthNum() {
        return limitMonthNum;
    }

    public void setLimitMonthNum(Long limitMonthNum) {
        this.limitMonthNum = limitMonthNum;
    }

    public String getWhetherInvalid() {
        return whetherInvalid;
    }

    public void setWhetherInvalid(String whetherInvalid) {
        this.whetherInvalid = whetherInvalid;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
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
    
    public String getWashCarCycleName() {
        return WashCarCycleType.ALL.get(this.getWashCarCycle()) == null ? "" : WashCarCycleType.valueOf(this.getWashCarCycle()).getName();
    }
    
    public String getWashTherInvalName(){
    	
    	return WashTherInvalIdType.ALL.get(this.getWhetherInvalid()) == null ? "" : WashTherInvalIdType.valueOf(this.getWhetherInvalid()).getName();
    }
}