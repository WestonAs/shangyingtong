package gnete.card.entity;

import gnete.card.entity.type.CostCycleType;
import gnete.card.entity.type.FeeFeeType;
import java.math.BigDecimal;
import java.util.Date;

public class CardMerchFee extends CardMerchFeeKey {
    private String feeType;

    private BigDecimal feeRate;

    private BigDecimal minAmt;

    private BigDecimal maxAmt;

    private String modifyDate;

    private String updateBy;
    
    private Date updateTime;
    
    private String costCycle;

    //新增
    private String merchName;
    private String branchName;

    private String merchFlag;
    
    private String curCode;

    private BigDecimal newUlMoney;
    
    private Long feeRuleId;
    
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

	public String getMerchName() {
		return merchName;
	}

	public void setMerchName(String merchName) {
		this.merchName = merchName;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getFeeTypeName(){
		return FeeFeeType.ALL.get(this.feeType) == null ? "" : FeeFeeType
				.valueOf(this.feeType).getName();
	}

	public String getCostCycle() {
		return costCycle;
	}

	public void setCostCycle(String costCycle) {
		this.costCycle = costCycle;
	}
	
	public String getCostCycleName(){
		return CostCycleType.valueOf(costCycle).getName();
	}

	public String getMerchFlag() {
		return merchFlag;
	}

	public void setMerchFlag(String merchFlag) {
		this.merchFlag = merchFlag;
	}

	public String getCurCode() {
		return curCode;
	}

	public void setCurCode(String curCode) {
		this.curCode = curCode;
	}

	public BigDecimal getNewUlMoney() {
		return newUlMoney;
	}

	public void setNewUlMoney(BigDecimal newUlMoney) {
		this.newUlMoney = newUlMoney;
	}

	public Long getFeeRuleId() {
		return feeRuleId;
	}

	public void setFeeRuleId(Long feeRuleId) {
		this.feeRuleId = feeRuleId;
	}
	
}