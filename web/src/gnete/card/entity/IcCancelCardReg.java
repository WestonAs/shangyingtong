package gnete.card.entity;

import gnete.card.entity.flag.ReversalFlag;
import gnete.card.entity.flag.YesOrNoFlag;
import gnete.card.entity.state.RegisterState;
import gnete.card.entity.type.IcCancelCardType;

import java.math.BigDecimal;
import java.util.Date;

public class IcCancelCardReg {
	
    private String id;

    private String cardId;

    private String acctId;

    private String custName;

    private String certType;

    private String certNo;

    private BigDecimal balanceAmt;

    private String cancelType;

    private String status;

    private String cardBranch;

    private String appOrgId;

    private String branchCode;

    private String updateUser;

    private Date updateTime;

    private String remark;

    private String cardSn;

    private String arqc;

    private String aqdt;

    private String arpc;

    private String chkRespCode;

    private String writeRespCode;

    private String writeScript;

    private String writeCardFlag;

    private String randomSessionId;

    private String reversalFlag;
    
    private BigDecimal feeAmt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public BigDecimal getBalanceAmt() {
        return balanceAmt;
    }

    public void setBalanceAmt(BigDecimal balanceAmt) {
        this.balanceAmt = balanceAmt;
    }

    public String getCancelType() {
        return cancelType;
    }

    public void setCancelType(String cancelType) {
        this.cancelType = cancelType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCardBranch() {
        return cardBranch;
    }

    public void setCardBranch(String cardBranch) {
        this.cardBranch = cardBranch;
    }

    public String getAppOrgId() {
        return appOrgId;
    }

    public void setAppOrgId(String appOrgId) {
        this.appOrgId = appOrgId;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
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

    public String getCardSn() {
        return cardSn;
    }

    public void setCardSn(String cardSn) {
        this.cardSn = cardSn;
    }

    public String getArqc() {
        return arqc;
    }

    public void setArqc(String arqc) {
        this.arqc = arqc;
    }

    public String getAqdt() {
        return aqdt;
    }

    public void setAqdt(String aqdt) {
        this.aqdt = aqdt;
    }

    public String getArpc() {
        return arpc;
    }

    public void setArpc(String arpc) {
        this.arpc = arpc;
    }

    public String getChkRespCode() {
        return chkRespCode;
    }

    public void setChkRespCode(String chkRespCode) {
        this.chkRespCode = chkRespCode;
    }

    public String getWriteRespCode() {
        return writeRespCode;
    }

    public void setWriteRespCode(String writeRespCode) {
        this.writeRespCode = writeRespCode;
    }

    public String getWriteScript() {
        return writeScript;
    }

    public void setWriteScript(String writeScript) {
        this.writeScript = writeScript;
    }

    public String getWriteCardFlag() {
        return writeCardFlag;
    }

    public void setWriteCardFlag(String writeCardFlag) {
        this.writeCardFlag = writeCardFlag;
    }

    public String getRandomSessionId() {
        return randomSessionId;
    }

    public void setRandomSessionId(String randomSessionId) {
        this.randomSessionId = randomSessionId;
    }

    public String getReversalFlag() {
        return reversalFlag;
    }

    public void setReversalFlag(String reversalFlag) {
        this.reversalFlag = reversalFlag;
    }

	public BigDecimal getFeeAmt() {
		return feeAmt;
	}

	public void setFeeAmt(BigDecimal feeAmt) {
		this.feeAmt = feeAmt;
	}
	
	/**
	 * 状态名
	 * @return
	 */
	public String getStatusName() {
		return RegisterState.ALL.get(status) == null ? "" : RegisterState.valueOf(status).getName();
	}
	
	/**
	 * 销卡类型
	 * @return
	 */
	public String getCancelTypeName() {
        return IcCancelCardType.ALL.get(cancelType) == null ? "" : IcCancelCardType.valueOf(cancelType).getName();
    }
	
	/**
	 * 冲正标志：00-未冲正，01-已冲正，02-冲正失败
	 * 
	 * @return
	 */
	public String getReversalFlagName() {
		return ReversalFlag.ALL.get(this.reversalFlag) == null ? "" : ReversalFlag.valueOf(this.reversalFlag).getName();
	}
	
	/**
	 * 是否写卡成功标志名
	 * 
	 * @return
	 */
	public String getWriteCardFlagName() {
		return YesOrNoFlag.ALL.get(this.writeCardFlag) == null ? "" : YesOrNoFlag.valueOf(this.writeCardFlag).getName();
	}

}