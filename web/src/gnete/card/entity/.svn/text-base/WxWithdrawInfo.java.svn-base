package gnete.card.entity;

import gnete.card.entity.state.WxWithDrawState;

import java.math.BigDecimal;
import java.util.Date;

public class WxWithdrawInfo {
    private Long withdrawBillNo;

    private String withdrawFromType;

    private String fromNo;

    private String opeSn;

    private String reqDate;

    private Date reqTime;

    private String transSn;

    private String cardId;

    private String extCardId;

    private String bankPwd;

    private BigDecimal billAmount;

    private BigDecimal availBal;

    private String rspCode;

    private String status;

    private String insNo;

    private String withdrawExtCard;

    private String bankAcct;

    private String bankCode;

    private String bankAcctName;

    private Date payDate;

    private String payBatSn;

    private String paySn;

    private String signValue;

    private String random;

    private Date updateTime;

    private String updateUser;

    private String postScript;

    public Long getWithdrawBillNo() {
        return withdrawBillNo;
    }

    public void setWithdrawBillNo(Long withdrawBillNo) {
        this.withdrawBillNo = withdrawBillNo;
    }

    public String getWithdrawFromType() {
        return withdrawFromType;
    }

    public void setWithdrawFromType(String withdrawFromType) {
        this.withdrawFromType = withdrawFromType;
    }

    public String getFromNo() {
        return fromNo;
    }

    public void setFromNo(String fromNo) {
        this.fromNo = fromNo;
    }

    public String getOpeSn() {
        return opeSn;
    }

    public void setOpeSn(String opeSn) {
        this.opeSn = opeSn;
    }

    public String getReqDate() {
        return reqDate;
    }

    public void setReqDate(String reqDate) {
        this.reqDate = reqDate;
    }

    public Date getReqTime() {
        return reqTime;
    }

    public void setReqTime(Date reqTime) {
        this.reqTime = reqTime;
    }

    public String getTransSn() {
        return transSn;
    }

    public void setTransSn(String transSn) {
        this.transSn = transSn;
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

    public String getBankPwd() {
        return bankPwd;
    }

    public void setBankPwd(String bankPwd) {
        this.bankPwd = bankPwd;
    }

    public BigDecimal getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(BigDecimal billAmount) {
        this.billAmount = billAmount;
    }

    public BigDecimal getAvailBal() {
        return availBal;
    }

    public void setAvailBal(BigDecimal availBal) {
        this.availBal = availBal;
    }

    public String getRspCode() {
        return rspCode;
    }

    public void setRspCode(String rspCode) {
        this.rspCode = rspCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInsNo() {
        return insNo;
    }

    public void setInsNo(String insNo) {
        this.insNo = insNo;
    }

    public String getWithdrawExtCard() {
        return withdrawExtCard;
    }

    public void setWithdrawExtCard(String withdrawExtCard) {
        this.withdrawExtCard = withdrawExtCard;
    }

    public String getBankAcct() {
        return bankAcct;
    }

    public void setBankAcct(String bankAcct) {
        this.bankAcct = bankAcct;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankAcctName() {
        return bankAcctName;
    }

    public void setBankAcctName(String bankAcctName) {
        this.bankAcctName = bankAcctName;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public String getPayBatSn() {
        return payBatSn;
    }

    public void setPayBatSn(String payBatSn) {
        this.payBatSn = payBatSn;
    }

    public String getPaySn() {
        return paySn;
    }

    public void setPaySn(String paySn) {
        this.paySn = paySn;
    }

    public String getSignValue() {
        return signValue;
    }

    public void setSignValue(String signValue) {
        this.signValue = signValue;
    }

    public String getRandom() {
        return random;
    }

    public void setRandom(String random) {
        this.random = random;
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

    public String getPostScript() {
        return postScript;
    }

    public void setPostScript(String postScript) {
        this.postScript = postScript;
    }
    
    public String getStatuName() {
    	return WxWithDrawState.ALL.get(this.getStatus()) == null ? "" : WxWithDrawState.valueOf(this.getStatus()).getName();
    }
}