package gnete.card.entity;

import gnete.card.entity.state.WashCarCheckState;
import gnete.card.entity.state.WashCarSvcMemberShipState;

import java.math.BigDecimal;
import java.util.Date;

public class WashCarSvcMbShipDues {
    private Long id;

    private String cardId;

    private String externalCardId;

    private String cardIssuer;

    private BigDecimal tollAmt;

    private String status;

    private String tollStartDate;

    private String tollEndDate;

    private Date posTollDate;

    private String posTollBankCard;

    private String recordActiveStatus;

    private Date insertTime;

    private String insertUser;

    private Date updateTime;

    private String updateUser;

    private String remark;

    private String checkStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getExternalCardId() {
        return externalCardId;
    }

    public void setExternalCardId(String externalCardId) {
        this.externalCardId = externalCardId;
    }

    public String getCardIssuer() {
        return cardIssuer;
    }

    public void setCardIssuer(String cardIssuer) {
        this.cardIssuer = cardIssuer;
    }

    public BigDecimal getTollAmt() {
        return tollAmt;
    }

    public void setTollAmt(BigDecimal tollAmt) {
        this.tollAmt = tollAmt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTollStartDate() {
        return tollStartDate;
    }

    public void setTollStartDate(String tollStartDate) {
        this.tollStartDate = tollStartDate;
    }

    public String getTollEndDate() {
        return tollEndDate;
    }

    public void setTollEndDate(String tollEndDate) {
        this.tollEndDate = tollEndDate;
    }

    public Date getPosTollDate() {
        return posTollDate;
    }

    public void setPosTollDate(Date posTollDate) {
        this.posTollDate = posTollDate;
    }

    public String getPosTollBankCard() {
        return posTollBankCard;
    }

    public void setPosTollBankCard(String posTollBankCard) {
        this.posTollBankCard = posTollBankCard;
    }

    public String getRecordActiveStatus() {
        return recordActiveStatus;
    }

    public void setRecordActiveStatus(String recordActiveStatus) {
        this.recordActiveStatus = recordActiveStatus;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public String getInsertUser() {
        return insertUser;
    }

    public void setInsertUser(String insertUser) {
        this.insertUser = insertUser;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus;
    }
    
    public String getStatusName(){
    	
    	return WashCarSvcMemberShipState.ALL.get(this.getStatus()) == null ? "" : WashCarSvcMemberShipState.valueOf(this.getStatus()).getName();
    }
    
    public String getCheckStatusName(){
    	return WashCarCheckState.ALL.get(this.getCheckStatus()) == null ? "" : WashCarCheckState.valueOf(this.getCheckStatus()).getName();
    }
}