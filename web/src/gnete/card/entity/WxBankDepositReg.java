package gnete.card.entity;

import gnete.card.entity.state.WxBankDepositState;

import java.math.BigDecimal;
import java.util.Date;

public class WxBankDepositReg {
    private Long depositId;

    private String wsSn;

    private String accessNo;

    private String cardId;

    private String extCardId;

    private BigDecimal depositAmount;

    private BigDecimal realDepositAmount;

    private String merchType;

    private String insId;

    private String pointSubclass;

    private String status;

    private String transSn;

    private String random;

    private String checkVerify;

    private String bankAcct;

    private String signValue;

    private String entryMode;

    private String condCode;

    private String acpter;

    private String acpterName;

    private String curCode;

    private String remark;

    private String reserved1;

    private Date updateTime;

    private String updateUser;

    private BigDecimal settAmt;

    private String settDate;

    private String validDate;

    private String rspCode;

    private String postScript;

    private String cvn2;

    public Long getDepositId() {
        return depositId;
    }

    public void setDepositId(Long depositId) {
        this.depositId = depositId;
    }

    public String getWsSn() {
        return wsSn;
    }

    public void setWsSn(String wsSn) {
        this.wsSn = wsSn;
    }

    public String getAccessNo() {
        return accessNo;
    }

    public void setAccessNo(String accessNo) {
        this.accessNo = accessNo;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getExtCardId() {
        return extCardId;
    }

    public void setExtCardId(String extCardId) {
        this.extCardId = extCardId;
    }

    public BigDecimal getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(BigDecimal depositAmount) {
        this.depositAmount = depositAmount;
    }

    public BigDecimal getRealDepositAmount() {
        return realDepositAmount;
    }

    public void setRealDepositAmount(BigDecimal realDepositAmount) {
        this.realDepositAmount = realDepositAmount;
    }

    public String getMerchType() {
        return merchType;
    }

    public void setMerchType(String merchType) {
        this.merchType = merchType;
    }

    public String getInsId() {
        return insId;
    }

    public void setInsId(String insId) {
        this.insId = insId;
    }

    public String getPointSubclass() {
        return pointSubclass;
    }

    public void setPointSubclass(String pointSubclass) {
        this.pointSubclass = pointSubclass;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTransSn() {
        return transSn;
    }

    public void setTransSn(String transSn) {
        this.transSn = transSn;
    }

    public String getRandom() {
        return random;
    }

    public void setRandom(String random) {
        this.random = random;
    }

    public String getCheckVerify() {
        return checkVerify;
    }

    public void setCheckVerify(String checkVerify) {
        this.checkVerify = checkVerify;
    }

    public String getBankAcct() {
        return bankAcct;
    }

    public void setBankAcct(String bankAcct) {
        this.bankAcct = bankAcct;
    }

    public String getSignValue() {
        return signValue;
    }

    public void setSignValue(String signValue) {
        this.signValue = signValue;
    }

    public String getEntryMode() {
        return entryMode;
    }

    public void setEntryMode(String entryMode) {
        this.entryMode = entryMode;
    }

    public String getCondCode() {
        return condCode;
    }

    public void setCondCode(String condCode) {
        this.condCode = condCode;
    }

    public String getAcpter() {
        return acpter;
    }

    public void setAcpter(String acpter) {
        this.acpter = acpter;
    }

    public String getAcpterName() {
        return acpterName;
    }

    public void setAcpterName(String acpterName) {
        this.acpterName = acpterName;
    }

    public String getCurCode() {
        return curCode;
    }

    public void setCurCode(String curCode) {
        this.curCode = curCode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getReserved1() {
        return reserved1;
    }

    public void setReserved1(String reserved1) {
        this.reserved1 = reserved1;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public BigDecimal getSettAmt() {
        return settAmt;
    }

    public void setSettAmt(BigDecimal settAmt) {
        this.settAmt = settAmt;
    }

    public String getSettDate() {
        return settDate;
    }

    public void setSettDate(String settDate) {
        this.settDate = settDate;
    }

    public String getValidDate() {
        return validDate;
    }

    public void setValidDate(String validDate) {
        this.validDate = validDate;
    }

    public String getRspCode() {
        return rspCode;
    }

    public void setRspCode(String rspCode) {
        this.rspCode = rspCode;
    }

    public String getPostScript() {
        return postScript;
    }

    public void setPostScript(String postScript) {
        this.postScript = postScript;
    }

    public String getCvn2() {
        return cvn2;
    }

    public void setCvn2(String cvn2) {
        this.cvn2 = cvn2;
    }
    
    public String getStatusName(){
    	return WxBankDepositState.ALL.get(this.getStatus()) == null ? "" : WxBankDepositState.valueOf(this.getStatus()).getName();
    }
}