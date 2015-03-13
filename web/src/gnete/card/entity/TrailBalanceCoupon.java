package gnete.card.entity;

import gnete.card.entity.flag.BalanceFlag;
import gnete.card.entity.type.IssType;

import java.math.BigDecimal;
import java.util.Date;

public class TrailBalanceCoupon extends TrailBalanceCouponKey {
    private String cardIssuer;
    
    private String issId;

    private String issType;

    private BigDecimal deltaAmt;

    private BigDecimal lastBal;

    private BigDecimal curBal;

    private String balanceFlag;

    private Date updateTime;

    private String className;
    
    public String getIssId() {
        return issId;
    }

    public void setIssId(String issId) {
        this.issId = issId;
    }

    public String getIssType() {
        return issType;
    }

    public void setIssType(String issType) {
        this.issType = issType;
    }

    public BigDecimal getDeltaAmt() {
        return deltaAmt;
    }

    public void setDeltaAmt(BigDecimal deltaAmt) {
        this.deltaAmt = deltaAmt;
    }

    public BigDecimal getLastBal() {
        return lastBal;
    }

    public void setLastBal(BigDecimal lastBal) {
        this.lastBal = lastBal;
    }

    public BigDecimal getCurBal() {
        return curBal;
    }

    public void setCurBal(BigDecimal curBal) {
        this.curBal = curBal;
    }

    public String getBalanceFlag() {
        return balanceFlag;
    }

    public void setBalanceFlag(String balanceFlag) {
        this.balanceFlag = balanceFlag;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    
    public String getBalanceFlagName(){
		return BalanceFlag.ALL.get(this.balanceFlag) == null ? "" : BalanceFlag.valueOf(this.balanceFlag).getName();
	}
    
    public String getIssTypeName(){
    	return IssType.ALL.get(this.issType) == null ? "" : IssType.valueOf(this.issType).getName();
    }

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getCardIssuer() {
		return cardIssuer;
	}

	public void setCardIssuer(String cardIssuer) {
		this.cardIssuer = cardIssuer;
	}
}