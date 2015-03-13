package gnete.card.entity;

import gnete.card.entity.state.WxPayDepositState;

import java.math.BigDecimal;
import java.util.Date;

public class WxPayDepositInfo {
    private Long chargeBillNo;

    private String fromType;

    private String fromNo;

    private String cardId;

    private String wsSn;

    private String transDate;

    private Date transTime;

    private String payBankAcct;

    private String cardIssuer;

    private String extCardId;

    private String providerNo;

    private String serviceId;

    private BigDecimal settAmount;

    private BigDecimal depositAmount;

    private String transSn;

    private String status;

    private String signValue;

    private String random;

    private String updateUser;

    private Date updateTime;

    private String postScript;

    private String rspCode;

    private BigDecimal realPayAmount;

    public Long getChargeBillNo() {
        return chargeBillNo;
    }

    public void setChargeBillNo(Long chargeBillNo) {
        this.chargeBillNo = chargeBillNo;
    }

    public String getFromType() {
        return fromType;
    }

    public void setFromType(String fromType) {
        this.fromType = fromType;
    }

    public String getFromNo() {
        return fromNo;
    }

    public void setFromNo(String fromNo) {
        this.fromNo = fromNo;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getWsSn() {
        return wsSn;
    }

    public void setWsSn(String wsSn) {
        this.wsSn = wsSn;
    }

    public String getTransDate() {
        return transDate;
    }

    public void setTransDate(String transDate) {
        this.transDate = transDate;
    }

    public Date getTransTime() {
        return transTime;
    }

    public void setTransTime(Date transTime) {
        this.transTime = transTime;
    }

    public String getPayBankAcct() {
        return payBankAcct;
    }

    public void setPayBankAcct(String payBankAcct) {
        this.payBankAcct = payBankAcct;
    }

    public String getCardIssuer() {
        return cardIssuer;
    }

    public void setCardIssuer(String cardIssuer) {
        this.cardIssuer = cardIssuer;
    }

    public String getExtCardId() {
        return extCardId;
    }

    public void setExtCardId(String extCardId) {
        this.extCardId = extCardId;
    }

    public String getProviderNo() {
        return providerNo;
    }

    public void setProviderNo(String providerNo) {
        this.providerNo = providerNo;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public BigDecimal getSettAmount() {
        return settAmount;
    }

    public void setSettAmount(BigDecimal settAmount) {
        this.settAmount = settAmount;
    }

    public BigDecimal getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(BigDecimal depositAmount) {
        this.depositAmount = depositAmount;
    }

    public String getTransSn() {
        return transSn;
    }

    public void setTransSn(String transSn) {
        this.transSn = transSn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getPostScript() {
        return postScript;
    }

    public void setPostScript(String postScript) {
        this.postScript = postScript;
    }

    public String getRspCode() {
        return rspCode;
    }

    public void setRspCode(String rspCode) {
        this.rspCode = rspCode;
    }

    public BigDecimal getRealPayAmount() {
        return realPayAmount;
    }

    public void setRealPayAmount(BigDecimal realPayAmount) {
        this.realPayAmount = realPayAmount;
    }
    
    public String getStatuName() {
    	return WxPayDepositState.ALL.get(this.getStatus()) == null ? "" : WxPayDepositState.valueOf(this.getStatus()).getName();
    }
}