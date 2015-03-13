package gnete.card.entity;

import java.util.Date;

import gnete.card.entity.state.AwdState;
import gnete.card.entity.type.TransType;

public class AwardReg extends AwardRegKey {
    private Integer prizeNo;

    private String awdStatus;

    private String cardId;

    private String transType;

    private String transSn;

    private String awdOptr;
    
    private String exchgOptr;

    private Date awdTime;

    private Date exchgTime;
    
    private String custName;

    private String credNo;
    
    public Integer getPrizeNo() {
        return prizeNo;
    }

    public void setPrizeNo(Integer prizeNo) {
        this.prizeNo = prizeNo;
    }

    public String getAwdStatus() {
        return awdStatus;
    }

    public void setAwdStatus(String awdStatus) {
        this.awdStatus = awdStatus;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public String getTransSn() {
        return transSn;
    }

    public void setTransSn(String transSn) {
        this.transSn = transSn;
    }

    public String getAwdOptr() {
        return awdOptr;
    }

    public void setAwdOptr(String awdOptr) {
        this.awdOptr = awdOptr;
    }

    public String getExchgOptr() {
        return exchgOptr;
    }

    public void setExchgOptr(String exchgOptr) {
        this.exchgOptr = exchgOptr;
    }

    public Date getAwdTime() {
        return awdTime;
    }

    public void setAwdTime(Date awdTime) {
        this.awdTime = awdTime;
    }

    public Date getExchgTime() {
        return exchgTime;
    }

    public void setExchgTime(Date exchgTime) {
        this.exchgTime = exchgTime;
    }
    
    public String getAwdStatusName(){
		return AwdState.ALL.get(this.awdStatus) == null ? "" : AwdState.valueOf(this.awdStatus).getName();
	}
	 
	public String getTransTypeName(){
		return TransType.ALL.get(this.transType) == null ? "" : TransType.valueOf(this.transType).getName();
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getCredNo() {
		return credNo;
	}

	public void setCredNo(String credNo) {
		this.credNo = credNo;
	}
}