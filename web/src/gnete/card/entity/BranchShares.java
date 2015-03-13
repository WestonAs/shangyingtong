package gnete.card.entity;

import gnete.card.entity.type.CardMerchFeeFeeType;
import gnete.card.entity.type.CostCycleType;
import java.math.BigDecimal;
import java.util.Date;

public class BranchShares extends BranchSharesKey {
    private String feeType;

    private String costCycle;

    private BigDecimal feeRate;

    private String modifyDate;

    private String updateBy;

    private Date updateTime;
    
    private BigDecimal newUlMoney;
    
    //新增
    private String branchName;

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

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getFeeTypeName() {
		return CardMerchFeeFeeType.valueOf(this.feeType).getName();
	}
	
	public String getCostCycleName() {
		return CostCycleType.valueOf(this.costCycle).getName();
	}

	public BigDecimal getNewUlMoney() {
		return newUlMoney;
	}

	public void setNewUlMoney(BigDecimal newUlMoney) {
		this.newUlMoney = newUlMoney;
	}
}