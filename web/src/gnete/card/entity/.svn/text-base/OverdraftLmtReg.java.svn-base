package gnete.card.entity;

import gnete.card.entity.state.RegisterState;
import java.math.BigDecimal;
import java.util.Date;

public class OverdraftLmtReg {
    private Long overdraftLmtId;

    private Long signCustId;

    private String acctId;

    private BigDecimal overdraft;

    private BigDecimal newOverdraft;

    private String status;

    private String updateUser;

    private Date updateTime;

    private String remark;
    
    private String cardId;
    
    private String branchCode;
    
    private String branchName;

    public Long getOverdraftLmtId() {
        return overdraftLmtId;
    }

    public void setOverdraftLmtId(Long overdraftLmtId) {
        this.overdraftLmtId = overdraftLmtId;
    }

    public Long getSignCustId() {
        return signCustId;
    }

    public void setSignCustId(Long signCustId) {
        this.signCustId = signCustId;
    }

    public String getAcctId() {
        return acctId;
    }

    public void setAcctId(String acctId) {
        this.acctId = acctId;
    }

    public BigDecimal getOverdraft() {
        return overdraft;
    }

    public void setOverdraft(BigDecimal overdraft) {
        this.overdraft = overdraft;
    }

    public BigDecimal getNewOverdraft() {
        return newOverdraft;
    }

    public void setNewOverdraft(BigDecimal newOverdraft) {
        this.newOverdraft = newOverdraft;
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
    
    public String getStatusName(){
		return RegisterState.ALL.get(this.status) == null ? "" : RegisterState.valueOf(this.status).getName();
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
    
}