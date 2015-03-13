package gnete.card.workflow.entity;

import java.util.Date;

public class WorkflowConfig extends WorkflowConfigKey {
    private String needAudit;

    private Integer auditLevel;

    private String updateUser;

    private Date updateTime;

    public String getNeedAudit() {
        return needAudit;
    }

    public void setNeedAudit(String needAudit) {
        this.needAudit = needAudit;
    }

    public Integer getAuditLevel() {
        return auditLevel;
    }

    public void setAuditLevel(Integer auditLevel) {
        this.auditLevel = auditLevel;
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
}