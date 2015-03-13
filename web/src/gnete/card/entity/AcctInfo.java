package gnete.card.entity;

import gnete.card.entity.state.CardState;
import gnete.card.entity.type.CardType;

import java.math.BigDecimal;

public class AcctInfo {
    private String acctId;

    private String cardIssuer;
    
    private String curCode;

    private String cardClass;

    private String cardSubclass;

    private String membLevel;

    private Long ptOpencard;

    private String membClass;

    private String discntClass;

    private String couponClass;

    private String frequencyClass;

    private String ptClass;

    private String signingCustomerId;

    private BigDecimal signingOverdraftLimit;

    private String effDate;

    private String expirDate;

    private String mustExpirDate;
    
    private BigDecimal consmUlimit;
    
    private Long tradeCnt;
    
    private BigDecimal initialChargeAmt;

    private BigDecimal accuChargeAmt;
    
    private String lastModifiedDate;
    
    //----------------------------- 新增表中没有的字段
    
    private String cardId;
    
    private String externalCardId;

    private String branchName;
    
    /** 卡失效日期 */
    private String cardExpirDate;
    
    /** 卡信息状态 */
    private String cardStatus;
    
    /** 返利资金可用余额 */
    private BigDecimal rebateAvlbBal;
    
    /** 充值资金可用余额 */
    private BigDecimal depositAvlbBal;

    
	public String getCardClassName() {
		return CardType.ALL.get(this.cardClass) == null ? "" : CardType.valueOf(this.cardClass).getName();
	}

	/** 获得卡信息的状态描述 */
	public String getCardStatusDesc() {
		return CardState.ALL.get(cardStatus) == null ? "" : CardState.valueOf(cardStatus).getName();
	}
    
	// ------------------------------- getter and setter followed------------------------
	
    public String getAcctId() {
        return acctId;
    }

    public void setAcctId(String acctId) {
        this.acctId = acctId;
    }

    public String getCardIssuer() {
        return cardIssuer;
    }

    public void setCardIssuer(String cardIssuer) {
        this.cardIssuer = cardIssuer;
    }

    public String getCardClass() {
        return cardClass;
    }

    public void setCardClass(String cardClass) {
        this.cardClass = cardClass;
    }

    public String getCardSubclass() {
        return cardSubclass;
    }

    public void setCardSubclass(String cardSubclass) {
        this.cardSubclass = cardSubclass;
    }

    public String getMembLevel() {
        return membLevel;
    }

    public void setMembLevel(String membLevel) {
        this.membLevel = membLevel;
    }

    public Long getPtOpencard() {
        return ptOpencard;
    }

    public void setPtOpencard(Long ptOpencard) {
        this.ptOpencard = ptOpencard;
    }

    public String getMembClass() {
        return membClass;
    }

    public void setMembClass(String membClass) {
        this.membClass = membClass;
    }

    public String getDiscntClass() {
        return discntClass;
    }

    public void setDiscntClass(String discntClass) {
        this.discntClass = discntClass;
    }

    public String getCouponClass() {
        return couponClass;
    }

    public void setCouponClass(String couponClass) {
        this.couponClass = couponClass;
    }

    public String getFrequencyClass() {
        return frequencyClass;
    }

    public void setFrequencyClass(String frequencyClass) {
        this.frequencyClass = frequencyClass;
    }

    public String getPtClass() {
        return ptClass;
    }

    public void setPtClass(String ptClass) {
        this.ptClass = ptClass;
    }

    public String getSigningCustomerId() {
        return signingCustomerId;
    }

    public void setSigningCustomerId(String signingCustomerId) {
        this.signingCustomerId = signingCustomerId;
    }

    public BigDecimal getSigningOverdraftLimit() {
        return signingOverdraftLimit;
    }

    public void setSigningOverdraftLimit(BigDecimal signingOverdraftLimit) {
        this.signingOverdraftLimit = signingOverdraftLimit;
    }

    public String getEffDate() {
        return effDate;
    }

    public void setEffDate(String effDate) {
        this.effDate = effDate;
    }

    public String getExpirDate() {
        return expirDate;
    }

    public void setExpirDate(String expirDate) {
        this.expirDate = expirDate;
    }

    public String getMustExpirDate() {
        return mustExpirDate;
    }

    public void setMustExpirDate(String mustExpirDate) {
        this.mustExpirDate = mustExpirDate;
    }

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCurCode(String curCode) {
		this.curCode = curCode;
	}

	public String getCurCode() {
		return curCode;
	}

	public Long getTradeCnt() {
		return tradeCnt;
	}

	public void setTradeCnt(Long tradeCnt) {
		this.tradeCnt = tradeCnt;
	}

	public BigDecimal getInitialChargeAmt() {
		return initialChargeAmt;
	}

	public void setInitialChargeAmt(BigDecimal initialChargeAmt) {
		this.initialChargeAmt = initialChargeAmt;
	}

	public BigDecimal getAccuChargeAmt() {
		return accuChargeAmt;
	}

	public void setAccuChargeAmt(BigDecimal accuChargeAmt) {
		this.accuChargeAmt = accuChargeAmt;
	}

	public String getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(String lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public void setConsmUlimit(BigDecimal consmUlimit) {
		this.consmUlimit = consmUlimit;
	}

	public BigDecimal getConsmUlimit() {
		return consmUlimit;
	}
	
	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public BigDecimal getRebateAvlbBal() {
		return rebateAvlbBal;
	}

	public void setRebateAvlbBal(BigDecimal rebateAvlbBal) {
		this.rebateAvlbBal = rebateAvlbBal;
	}

	public BigDecimal getDepositAvlbBal() {
		return depositAvlbBal;
	}

	public void setDepositAvlbBal(BigDecimal depositAvlbBal) {
		this.depositAvlbBal = depositAvlbBal;
	}

	public String getCardExpirDate() {
		return cardExpirDate;
	}

	public void setCardExpirDate(String cardExpirDate) {
		this.cardExpirDate = cardExpirDate;
	}

	public String getExternalCardId() {
		return externalCardId;
	}

	public void setExternalCardId(String externalCardId) {
		this.externalCardId = externalCardId;
	}

}