package gnete.card.entity;

import java.math.BigDecimal;
import java.util.Date;

public class ReleaseCardFeeHis {
	private Long id;

    private String branchCode;

    private String merchId;

    private String transType;

    private String cardBin;

    private String feeType;

    private String costCycle;

    private BigDecimal ulMoney;

    private BigDecimal feeRate;

    private String modifyDate;

    private String updateBy;

    private Date updateTime;
    
    private String curCode;
    
    private String feePrinciple;

    private String effDate;

    private String expirDate;

    private Long feeRuleId;
    
    private BigDecimal minAmt;

    private BigDecimal maxAmt;
    
    private String groupFlag;

    private String chlCode;
    
    private String feeCycleType;

    private String adjustCycleType;

    private Short adjustMonths;
    
    private String status;

    private String feeDate;
    
    private BigDecimal amtOrCnt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public String getCostCycle() {
        return costCycle;
    }

    public void setCostCycle(String costCycle) {
        this.costCycle = costCycle;
    }

    public BigDecimal getUlMoney() {
        return ulMoney;
    }

    public void setUlMoney(BigDecimal ulMoney) {
        this.ulMoney = ulMoney;
    }

    public BigDecimal getFeeRate() {
        return feeRate;
    }

    public void setFeeRate(BigDecimal feeRate) {
        this.feeRate = feeRate;
    }

    public String getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

	public String getMerchId() {
		return merchId;
	}

	public void setMerchId(String merchId) {
		this.merchId = merchId;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getCardBin() {
		return cardBin;
	}

	public void setCardBin(String cardBin) {
		this.cardBin = cardBin;
	}

	public String getCurCode() {
		return curCode;
	}

	public void setCurCode(String curCode) {
		this.curCode = curCode;
	}

	public String getFeePrinciple() {
		return feePrinciple;
	}

	public void setFeePrinciple(String feePrinciple) {
		this.feePrinciple = feePrinciple;
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

	public Long getFeeRuleId() {
		return feeRuleId;
	}

	public void setFeeRuleId(Long feeRuleId) {
		this.feeRuleId = feeRuleId;
	}

	public BigDecimal getMinAmt() {
		return minAmt;
	}

	public void setMinAmt(BigDecimal minAmt) {
		this.minAmt = minAmt;
	}

	public BigDecimal getMaxAmt() {
		return maxAmt;
	}

	public void setMaxAmt(BigDecimal maxAmt) {
		this.maxAmt = maxAmt;
	}

	public String getGroupFlag() {
		return groupFlag;
	}

	public void setGroupFlag(String groupFlag) {
		this.groupFlag = groupFlag;
	}

	public String getChlCode() {
		return chlCode;
	}

	public void setChlCode(String chlCode) {
		this.chlCode = chlCode;
	}

	public String getFeeCycleType() {
		return feeCycleType;
	}

	public void setFeeCycleType(String feeCycleType) {
		this.feeCycleType = feeCycleType;
	}

	public String getAdjustCycleType() {
		return adjustCycleType;
	}

	public void setAdjustCycleType(String adjustCycleType) {
		this.adjustCycleType = adjustCycleType;
	}

	public Short getAdjustMonths() {
		return adjustMonths;
	}

	public void setAdjustMonths(Short adjustMonths) {
		this.adjustMonths = adjustMonths;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFeeDate() {
		return feeDate;
	}

	public void setFeeDate(String feeDate) {
		this.feeDate = feeDate;
	}

	public BigDecimal getAmtOrCnt() {
		return amtOrCnt;
	}

	public void setAmtOrCnt(BigDecimal amtOrCnt) {
		this.amtOrCnt = amtOrCnt;
	}
}