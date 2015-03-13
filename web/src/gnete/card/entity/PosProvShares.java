package gnete.card.entity;

import gnete.card.entity.type.CostCycleType;
import gnete.card.entity.type.PosManageSharesFeeType;
import java.math.BigDecimal;
import java.util.Date;

public class PosProvShares extends PosProvSharesKey {
    private String feeType;

    private String costCycle;

    private BigDecimal feeRate;

    private String modifyDate;

    private String updateBy;

    private Date updateTime;
    
    //新增
    private String posProvName;

    private String branchName;

    private BigDecimal newUlMoney;
    
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

	public String getPosProvName() {
		return posProvName;
	}

	public void setPosProvName(String posProvName) {
		this.posProvName = posProvName;
	}
	
	public String getCostCycleName(){
		return CostCycleType.valueOf(costCycle).getName();
	}
	public String getFeeTypeName(){
		return PosManageSharesFeeType.valueOf(feeType).getName();
	}

	public BigDecimal getNewUlMoney() {
		return newUlMoney;
	}

	public void setNewUlMoney(BigDecimal newUlMoney) {
		this.newUlMoney = newUlMoney;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	
}