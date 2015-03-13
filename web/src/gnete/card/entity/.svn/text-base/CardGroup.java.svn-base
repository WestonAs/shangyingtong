package gnete.card.entity;

import gnete.card.entity.state.CommonState;

import java.util.Date;

public class CardGroup {
    private String branchCode;

    private String groupId;

    private String status;

    private String updateBy;

    private Date updateTime;

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
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
    	return CommonState.ALL.get(this.status) == null ? "" : CommonState.valueOf(this.status).getName();
    }
}