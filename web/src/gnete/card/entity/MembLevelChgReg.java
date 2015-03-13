package gnete.card.entity;

import gnete.card.entity.state.MembLevelChgState;

import java.util.Date;

public class MembLevelChgReg {
	private Long id;

    private String cardId;

    private String origLevel;

    private String curLevel;

    private String membClass;

    private String cardIssuer;

    private Date updateTime;

    private String updateBy;

    private String remark;
    
    private String procProcStatus;

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getOrigLevel() {
        return origLevel;
    }

    public void setOrigLevel(String origLevel) {
        this.origLevel = origLevel;
    }

    public String getCurLevel() {
        return curLevel;
    }

    public void setCurLevel(String curLevel) {
        this.curLevel = curLevel;
    }

    public String getMembClass() {
        return membClass;
    }

    public void setMembClass(String membClass) {
        this.membClass = membClass;
    }

    public String getCardIssuer() {
        return cardIssuer;
    }

    public void setCardIssuer(String cardIssuer) {
        this.cardIssuer = cardIssuer;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    public String getProcStatus() {
		return procProcStatus;
	}

	public void setProcStatus(String procProcStatus) {
		this.procProcStatus = procProcStatus;
	}
	
	public String getProcStatusName() {
		return procProcStatus == null ? "":MembLevelChgState.valueOf(procProcStatus).getName();
	}

}