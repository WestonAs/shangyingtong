package gnete.card.entity;

import gnete.card.entity.flag.YesOrNoFlag;
import gnete.card.entity.state.RegisterState;

import java.math.BigDecimal;
import java.util.Date;

public class IcCardParaModifyReg {
  
	private String id;

    private String cardId;

    private String cardSubClass;

    private String autoDepositFlag;

    private BigDecimal autoDepositAmt;

    private BigDecimal balanceLimit;

    private BigDecimal amountLimit;

    private String cardBranch;

    private String appOrgId;

    private String branchCode;

    private String updateUser;

    private Date updateTime;

    private String status;

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

    public String getCardSubClass() {
        return cardSubClass;
    }

    public void setCardSubClass(String cardSubClass) {
        this.cardSubClass = cardSubClass;
    }

    public String getAutoDepositFlag() {
        return autoDepositFlag;
    }

    public void setAutoDepositFlag(String autoDepositFlag) {
        this.autoDepositFlag = autoDepositFlag;
    }

    public BigDecimal getAutoDepositAmt() {
        return autoDepositAmt;
    }

    public void setAutoDepositAmt(BigDecimal autoDepositAmt) {
        this.autoDepositAmt = autoDepositAmt;
    }

    public BigDecimal getBalanceLimit() {
        return balanceLimit;
    }

    public void setBalanceLimit(BigDecimal balanceLimit) {
        this.balanceLimit = balanceLimit;
    }

    public BigDecimal getAmountLimit() {
        return amountLimit;
    }

    public void setAmountLimit(BigDecimal amountLimit) {
        this.amountLimit = amountLimit;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
	
	public String getAutoDepositFlagName() {
		return YesOrNoFlag.ALL.get(autoDepositFlag) == null ? "" : YesOrNoFlag.valueOf(autoDepositFlag).getName();
	}
	
	public String getWriteCardFlagName() {
        return YesOrNoFlag.ALL.get(writeCardFlag) == null ? "" : YesOrNoFlag.valueOf(writeCardFlag).getName();
    }
	
	public String getStatusName() {
		return RegisterState.ALL.get(status) == null ? "" : RegisterState.valueOf(status).getName();
	}

}