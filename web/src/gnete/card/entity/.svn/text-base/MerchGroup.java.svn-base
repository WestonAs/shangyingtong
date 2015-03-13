package gnete.card.entity;

import gnete.card.entity.state.CommonState;

import java.util.Date;

public class MerchGroup {
    private String groupId;

    private String groupName;

    private String manageBranch;

    private String cardIssuer;

    private String status;

    private Date insertTime;

    private String updateBy;

    private Date updateTime;


    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getManageBranch() {
        return manageBranch;
    }

    public void setManageBranch(String manageBranch) {
        this.manageBranch = manageBranch;
    }

    public String getCardIssuer() {
        return cardIssuer;
    }

    public void setCardIssuer(String cardIssuer) {
        this.cardIssuer = cardIssuer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
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
    	return CommonState.ALL.get(this.status) == null ? "" : CommonState.valueOf(this.status).getName();
    }

}