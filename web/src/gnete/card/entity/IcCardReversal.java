package gnete.card.entity;

import java.util.Date;

public class IcCardReversal {
    private String batchId;

    private String refId;

    private String reversalType;

    private String autoFlag;

    private String reversalBranch;

    private String status;

    private String updateUser;

    private Date updateTime;

    private String remark;

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public String getReversalType() {
        return reversalType;
    }

    public void setReversalType(String reversalType) {
        this.reversalType = reversalType;
    }

    public String getAutoFlag() {
        return autoFlag;
    }

    public void setAutoFlag(String autoFlag) {
        this.autoFlag = autoFlag;
    }

    public String getReversalBranch() {
        return reversalBranch;
    }

    public void setReversalBranch(String reversalBranch) {
        this.reversalBranch = reversalBranch;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
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
}