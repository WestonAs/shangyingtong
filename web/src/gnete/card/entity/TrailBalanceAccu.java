package gnete.card.entity;

import gnete.card.entity.flag.BalanceFlag;

import java.math.BigDecimal;
import java.util.Date;

public class TrailBalanceAccu extends TrailBalanceAccuKey {
    private String cardIssuer;

    private BigDecimal consumeAmt;

    private BigDecimal expirAmt;

    private BigDecimal sellCardAmt;

    private BigDecimal posRechargeAmt;

    private BigDecimal activateSellCardAmt;

    private BigDecimal rechargeAmt;

    private BigDecimal activateRechargeAmt;
    
    private BigDecimal retransAmt;
    
    private BigDecimal adjustAmt;

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

    public BigDecimal getConsumeAmt() {
        return consumeAmt;
    }

    public void setConsumeAmt(BigDecimal consumeAmt) {
        this.consumeAmt = consumeAmt;
    }

    public BigDecimal getExpirAmt() {
        return expirAmt;
    }

    public void setExpirAmt(BigDecimal expirAmt) {
        this.expirAmt = expirAmt;
    }

    public BigDecimal getSellCardAmt() {
        return sellCardAmt;
    }

    public void setSellCardAmt(BigDecimal sellCardAmt) {
        this.sellCardAmt = sellCardAmt;
    }

    public BigDecimal getPosRechargeAmt() {
        return posRechargeAmt;
    }

    public void setPosRechargeAmt(BigDecimal posRechargeAmt) {
        this.posRechargeAmt = posRechargeAmt;
    }

    public BigDecimal getActivateSellCardAmt() {
        return activateSellCardAmt;
    }

    public void setActivateSellCardAmt(BigDecimal activateSellCardAmt) {
        this.activateSellCardAmt = activateSellCardAmt;
    }

    public BigDecimal getRechargeAmt() {
        return rechargeAmt;
    }

    public void setRechargeAmt(BigDecimal rechargeAmt) {
        this.rechargeAmt = rechargeAmt;
    }

    public BigDecimal getActivateRechargeAmt() {
        return activateRechargeAmt;
    }

    public void setActivateRechargeAmt(BigDecimal activateRechargeAmt) {
        this.activateRechargeAmt = activateRechargeAmt;
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

	public BigDecimal getRetransAmt() {
		return retransAmt;
	}

	public void setRetransAmt(BigDecimal retransAmt) {
		this.retransAmt = retransAmt;
	}

	public BigDecimal getAdjustAmt() {
		return adjustAmt;
	}

	public void setAdjustAmt(BigDecimal adjustAmt) {
		this.adjustAmt = adjustAmt;
	}
}