package gnete.card.entity;

import gnete.card.entity.state.RegisterState;
import gnete.card.entity.type.CancelCardFlagType;
import gnete.card.entity.type.CertType;

import java.math.BigDecimal;
import java.util.Date;

public class CancelCardReg {
    private Long cancelCardId;

    private String cardId;

    private String acctId;

    private String custName;

    private String certType;

    private String certNo;

    private BigDecimal returnAmt;

    private String flag;

    private BigDecimal expenses;

    private String status;

    private String updateUser;

    private Date updateTime;

    private String remark;

    private String branchCode;

    public Long getCancelCardId() {
        return cancelCardId;
    }

    public void setCancelCardId(Long cancelCardId) {
        this.cancelCardId = cancelCardId;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getAcctId() {
        return acctId;
    }

    public void setAcctId(String acctId) {
        this.acctId = acctId;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCertType() {
        return certType;
    }

    public void setCertType(String certType) {
        this.certType = certType;
    }

    public String getCertNo() {
        return certNo;
    }

    public void setCertNo(String certNo) {
        this.certNo = certNo;
    }

    public BigDecimal getReturnAmt() {
        return returnAmt;
    }

    public void setReturnAmt(BigDecimal returnAmt) {
        this.returnAmt = returnAmt;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public BigDecimal getExpenses() {
        return expenses;
    }

    public void setExpenses(BigDecimal expenses) {
        this.expenses = expenses;
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

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }
    
    public String getStatusName() {
		return RegisterState.ALL.get(this.status) == null ? "" : RegisterState
				.valueOf(this.status).getName();
	}
    
    public String getFlagName() {
    	return CancelCardFlagType.ALL.get(this.flag) == null ? "" : CancelCardFlagType
    			.valueOf(this.flag).getName();
    }
    
    public String getCertTypeName(){
		return CertType.ALL.get(this.certType) == null ? "" : CertType.valueOf(this.certType).getName();
	}
}