package gnete.card.entity;

import gnete.card.entity.flag.BalanceFlag;

import java.math.BigDecimal;
import java.util.Date;

public class TrailBalancePoint extends TrailBalancePointKey {
    private String cardIssuer;

    private BigDecimal deltaAmt;

    private BigDecimal lastBal;

    private BigDecimal curBal;

    private String balanceFlag;

    private Date updateTime;
    
    private String className;

    public String getCardIssuer() {
        return cardIssuer;
    }

    public void setCardIssuer(String cardIssuer) {
        this.cardIssuer = cardIssuer;
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

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
}