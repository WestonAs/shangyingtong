package gnete.card.entity;

import java.math.BigDecimal;
import java.util.Date;

public class CardMerchFeeHis {
	private Long id;

    private String branchCode;

    private String merchId;

    private String transType;

    private String cardBin;

    private String feeType;

    private BigDecimal feeRate;

    private BigDecimal minAmt;

    private BigDecimal maxAmt;

    private String costCycle;

    private String modifyDate;

    private String updateBy;

    private Date updateTime;
    
    private BigDecimal ulMoney;
    
    private String curCode;

    private Long feeRuleId;
    
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

    public String getMerchId() {
        return merchId;
    }

    public void setMerchId(String merchId) {
        this.merchId = merchId;
    }


    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public BigDecimal getFeeRate() {
        return feeRate;
    }

    public void setFeeRate(BigDecimal feeRate) {
        this.feeRate = feeRate;
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

	public String getCostCycle() {
		return costCycle;
	}

	public void setCostCycle(String costCycle) {
		this.costCycle = costCycle;
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

	public BigDecimal getUlMoney() {
		return ulMoney;
	}

	public void setUlMoney(BigDecimal ulMoney) {
		this.ulMoney = ulMoney;
	}

	public String getCurCode() {
		return curCode;
	}

	public void setCurCode(String curCode) {
		this.curCode = curCode;
	}

	public Long getFeeRuleId() {
		return feeRuleId;
	}

	public void setFeeRuleId(Long feeRuleId) {
		this.feeRuleId = feeRuleId;
	}

}