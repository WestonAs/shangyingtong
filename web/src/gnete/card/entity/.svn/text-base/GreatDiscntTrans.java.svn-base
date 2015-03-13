package gnete.card.entity;

import gnete.card.entity.state.GreatDiscntTransState;
import gnete.card.entity.type.PayWayType;
import gnete.card.entity.type.TransType;

import java.math.BigDecimal;
import java.util.Date;

public class GreatDiscntTrans {
    private String transSn;

    private String cardId;

    private BigDecimal transAmt;

    private BigDecimal settAmt;

    private String transType;

    private String settDate;

    private String cardIssuer;

    private String merchNo;

    private String greatDiscntProtclId;

    private String payWay;

    private BigDecimal issuerRepaidHolderAmt;

    private BigDecimal merchRepaidIssuerAmt;

    private String payStatus;

    private String payDate;

    private Date insertTime;

    private Date updateTime;

    private String remark;

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

    public BigDecimal getTransAmt() {
        return transAmt;
    }

    public void setTransAmt(BigDecimal transAmt) {
        this.transAmt = transAmt;
    }

    public BigDecimal getSettAmt() {
        return settAmt;
    }

    public void setSettAmt(BigDecimal settAmt) {
        this.settAmt = settAmt;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public String getSettDate() {
        return settDate;
    }

    public void setSettDate(String settDate) {
        this.settDate = settDate;
    }

    public String getCardIssuer() {
        return cardIssuer;
    }

    public void setCardIssuer(String cardIssuer) {
        this.cardIssuer = cardIssuer;
    }

    public String getMerchNo() {
        return merchNo;
    }

    public void setMerchNo(String merchNo) {
        this.merchNo = merchNo;
    }

    public String getGreatDiscntProtclId() {
        return greatDiscntProtclId;
    }

    public void setGreatDiscntProtclId(String greatDiscntProtclId) {
        this.greatDiscntProtclId = greatDiscntProtclId;
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    public BigDecimal getIssuerRepaidHolderAmt() {
        return issuerRepaidHolderAmt;
    }

    public void setIssuerRepaidHolderAmt(BigDecimal issuerRepaidHolderAmt) {
        this.issuerRepaidHolderAmt = issuerRepaidHolderAmt;
    }

    public BigDecimal getMerchRepaidIssuerAmt() {
        return merchRepaidIssuerAmt;
    }

    public void setMerchRepaidIssuerAmt(BigDecimal merchRepaidIssuerAmt) {
        this.merchRepaidIssuerAmt = merchRepaidIssuerAmt;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
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
    
    public String getPayWayName() {
        return payWay==null ? "":PayWayType.valueOf(payWay).getName();
    }
    
    public String getPayStatusName() {
        return payStatus==null ? "":GreatDiscntTransState.valueOf(payStatus).getName();
    }
    
    public String getTransTypeName() {
    	 return transType==null ? "":TransType.valueOf(transType).getName();
    }
}