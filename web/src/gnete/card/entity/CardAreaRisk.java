package gnete.card.entity;

import gnete.card.entity.type.TransType;

import java.lang.String;
import java.util.Date;

public class CardAreaRisk {
    private String transSn;

    private String cardId;

    private String merNo;

    private String areaCode;

    private String transType;

    private Date rcvTime;

    private String proStatus;

    private String settDate;

    private String cardIssuer;

    private Date isnertTime;

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

    public String getMerNo() {
        return merNo;
    }

    public void setMerNo(String merNo) {
        this.merNo = merNo;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public Date getRcvTime() {
        return rcvTime;
    }

    public void setRcvTime(Date rcvTime) {
        this.rcvTime = rcvTime;
    }

    public String getProStatus() {
        return proStatus;
    }

    public void setProStatus(String proStatus) {
        this.proStatus = proStatus;
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

    public Date getIsnertTime() {
        return isnertTime;
    }

    public void setIsnertTime(Date isnertTime) {
        this.isnertTime = isnertTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTransTypeName() {
        return transType == null?"":TransType.valueOf(transType).getName();
    }
}