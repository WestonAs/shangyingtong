package gnete.card.entity;

import gnete.card.entity.state.CardFreeNumState;

import java.util.Date;

public class CardissuerFreeNum extends CardissuerFreeNumKey {

    private String issId;

    private Integer sendNum;

    private Integer usedNum;

    private String status;

    private String updateBy;

    private Date updateTime;

    public String getIssId() {
        return issId;
    }

    public void setIssId(String issId) {
        this.issId = issId;
    }

    public Integer getSendNum() {
        return sendNum;
    }

    public void setSendNum(Integer sendNum) {
        this.sendNum = sendNum;
    }

    public Integer getUsedNum() {
        return usedNum;
    }

    public void setUsedNum(Integer usedNum) {
        this.usedNum = usedNum;
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
    
    public String getStatusName() {
    	return CardFreeNumState.ALL.get(status) == null ? "" : CardFreeNumState.valueOf(status).getName();
    }
}