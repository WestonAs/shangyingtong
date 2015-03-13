package gnete.card.entity;

import gnete.card.entity.state.WxBillState;
import gnete.card.entity.type.WxBillType;

import java.math.BigDecimal;
import java.util.Date;

public class WxBillInfo {
    private Long billNo;

    private String billFromType;

    private String fromNo;

    private String billType;

    private String insNo;

    private String merchNo;

    private String merchSubacct;

    private String recvCardId;

    private String casherExtCard;

    private String payerCardId;

    private String autoCharge;

    private String payAccessType;

    private String payFromNo;

    private String payExtFlag;

    private String password;

    private String rtnPassword;

    private String encryptType;

    private BigDecimal billAmount;

    private BigDecimal realPayAmount;

    private String status;

    private String transSn;

    private String relateDepTransSn;

    private Date billCommitTime;

    private Date billExpirtDate;

    private Date billPayTime;

    private Date rtnGoodsTime;

    private String billBrief;

    private String billRemark;

    private String billCreateFlag;

    private String billCommitFlag;

    private String revocationType;

    private String billRevocFlag;

    private String biilPayFlag;

    private String rtnGoodsSn;

    private String signValue;

    private String random;

    private Date updateTime;

    private String updateUser;

    private String rspCode;

    private String postScript;

    public Long getBillNo() {
        return billNo;
    }

    public void setBillNo(Long billNo) {
        this.billNo = billNo;
    }

    public String getBillFromType() {
        return billFromType;
    }

    public void setBillFromType(String billFromType) {
        this.billFromType = billFromType;
    }

    public String getFromNo() {
        return fromNo;
    }

    public void setFromNo(String fromNo) {
        this.fromNo = fromNo;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public String getInsNo() {
        return insNo;
    }

    public void setInsNo(String insNo) {
        this.insNo = insNo;
    }

    public String getMerchNo() {
        return merchNo;
    }

    public void setMerchNo(String merchNo) {
        this.merchNo = merchNo;
    }

    public String getMerchSubacct() {
        return merchSubacct;
    }

    public void setMerchSubacct(String merchSubacct) {
        this.merchSubacct = merchSubacct;
    }

    public String getRecvCardId() {
        return recvCardId;
    }

    public void setRecvCardId(String recvCardId) {
        this.recvCardId = recvCardId;
    }

    public String getCasherExtCard() {
        return casherExtCard;
    }

    public void setCasherExtCard(String casherExtCard) {
        this.casherExtCard = casherExtCard;
    }

    public String getPayerCardId() {
        return payerCardId;
    }

    public void setPayerCardId(String payerCardId) {
        this.payerCardId = payerCardId;
    }

    public String getAutoCharge() {
        return autoCharge;
    }

    public void setAutoCharge(String autoCharge) {
        this.autoCharge = autoCharge;
    }

    public String getPayAccessType() {
        return payAccessType;
    }

    public void setPayAccessType(String payAccessType) {
        this.payAccessType = payAccessType;
    }

    public String getPayFromNo() {
        return payFromNo;
    }

    public void setPayFromNo(String payFromNo) {
        this.payFromNo = payFromNo;
    }

    public String getPayExtFlag() {
        return payExtFlag;
    }

    public void setPayExtFlag(String payExtFlag) {
        this.payExtFlag = payExtFlag;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRtnPassword() {
        return rtnPassword;
    }

    public void setRtnPassword(String rtnPassword) {
        this.rtnPassword = rtnPassword;
    }

    public String getEncryptType() {
        return encryptType;
    }

    public void setEncryptType(String encryptType) {
        this.encryptType = encryptType;
    }

    public BigDecimal getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(BigDecimal billAmount) {
        this.billAmount = billAmount;
    }

    public BigDecimal getRealPayAmount() {
        return realPayAmount;
    }

    public void setRealPayAmount(BigDecimal realPayAmount) {
        this.realPayAmount = realPayAmount;
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

    public String getRelateDepTransSn() {
        return relateDepTransSn;
    }

    public void setRelateDepTransSn(String relateDepTransSn) {
        this.relateDepTransSn = relateDepTransSn;
    }

    public Date getBillCommitTime() {
        return billCommitTime;
    }

    public void setBillCommitTime(Date billCommitTime) {
        this.billCommitTime = billCommitTime;
    }

    public Date getBillExpirtDate() {
        return billExpirtDate;
    }

    public void setBillExpirtDate(Date billExpirtDate) {
        this.billExpirtDate = billExpirtDate;
    }

    public Date getBillPayTime() {
        return billPayTime;
    }

    public void setBillPayTime(Date billPayTime) {
        this.billPayTime = billPayTime;
    }

    public Date getRtnGoodsTime() {
        return rtnGoodsTime;
    }

    public void setRtnGoodsTime(Date rtnGoodsTime) {
        this.rtnGoodsTime = rtnGoodsTime;
    }

    public String getBillBrief() {
        return billBrief;
    }

    public void setBillBrief(String billBrief) {
        this.billBrief = billBrief;
    }

    public String getBillRemark() {
        return billRemark;
    }

    public void setBillRemark(String billRemark) {
        this.billRemark = billRemark;
    }

    public String getBillCreateFlag() {
        return billCreateFlag;
    }

    public void setBillCreateFlag(String billCreateFlag) {
        this.billCreateFlag = billCreateFlag;
    }

    public String getBillCommitFlag() {
        return billCommitFlag;
    }

    public void setBillCommitFlag(String billCommitFlag) {
        this.billCommitFlag = billCommitFlag;
    }

    public String getRevocationType() {
        return revocationType;
    }

    public void setRevocationType(String revocationType) {
        this.revocationType = revocationType;
    }

    public String getBillRevocFlag() {
        return billRevocFlag;
    }

    public void setBillRevocFlag(String billRevocFlag) {
        this.billRevocFlag = billRevocFlag;
    }

    public String getBiilPayFlag() {
        return biilPayFlag;
    }

    public void setBiilPayFlag(String biilPayFlag) {
        this.biilPayFlag = biilPayFlag;
    }

    public String getRtnGoodsSn() {
        return rtnGoodsSn;
    }

    public void setRtnGoodsSn(String rtnGoodsSn) {
        this.rtnGoodsSn = rtnGoodsSn;
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
    
    public String getStatusName(){
    	return WxBillState.ALL.get(this.getStatus()) == null ? "" : WxBillState.valueOf(this.getStatus()).getName();
    }
    
    public String getBillTypeName(){
    	return WxBillType.ALL.get(this.getBillType()) == null ? "" : WxBillType.valueOf(this.getBillType()).getName();
    }
}