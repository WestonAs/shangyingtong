package gnete.card.entity;

import gnete.card.entity.state.RegisterState;
import gnete.etc.Symbol;

import java.math.BigDecimal;
import java.util.Date;

public class DepositPointReg {
    private Long depositBatchId;

    private String cardId;

    private String cardBranch;

    private String depositBranch;

    private String ptClass;

    private BigDecimal ptPoint;

    private BigDecimal refAmt;

    private String status;

    private String entryUserid;

    private String signature;

    private String randomSessionid;

    private String fileDeposit;

    private String depositDate;

    private String updateUser;

    private Date updateTime;

    private String remark;
    
    private String strCardId;

    private String endCardId;

    // 添加非表中字段
    /** 积分类型名 */
    private String className;
    
    public Long getDepositBatchId() {
        return depositBatchId;
    }

    public void setDepositBatchId(Long depositBatchId) {
        this.depositBatchId = depositBatchId;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCardBranch() {
        return cardBranch;
    }

    public void setCardBranch(String cardBranch) {
        this.cardBranch = cardBranch;
    }

    public String getDepositBranch() {
        return depositBranch;
    }

    public void setDepositBranch(String depositBranch) {
        this.depositBranch = depositBranch;
    }

    public String getPtClass() {
        return ptClass;
    }

    public void setPtClass(String ptClass) {
        this.ptClass = ptClass;
    }

    public BigDecimal getRefAmt() {
        return refAmt;
    }

    public void setRefAmt(BigDecimal refAmt) {
        this.refAmt = refAmt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEntryUserid() {
        return entryUserid;
    }

    public void setEntryUserid(String entryUserid) {
        this.entryUserid = entryUserid;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getRandomSessionid() {
        return randomSessionid;
    }

    public void setRandomSessionid(String randomSessionid) {
        this.randomSessionid = randomSessionid;
    }

    public String getFileDeposit() {
        return fileDeposit;
    }

    public String getFileDepositName() {
    	return Symbol.YES.equals(fileDeposit) ? "是" : "否";
    }

    public void setFileDeposit(String fileDeposit) {
        this.fileDeposit = fileDeposit;
    }

    public String getDepositDate() {
        return depositDate;
    }

    public void setDepositDate(String depositDate) {
        this.depositDate = depositDate;
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

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public BigDecimal getPtPoint() {
		return ptPoint;
	}

	public void setPtPoint(BigDecimal ptPoint) {
		this.ptPoint = ptPoint;
	}
	
	public String getStatusName() {
		return RegisterState.ALL.get(status) == null ? "" : RegisterState.valueOf(status).getName();
	}

	public String getStrCardId() {
		return strCardId;
	}

	public void setStrCardId(String strCardId) {
		this.strCardId = strCardId;
	}

	public String getEndCardId() {
		return endCardId;
	}

	public void setEndCardId(String endCardId) {
		this.endCardId = endCardId;
	}
}