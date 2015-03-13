package gnete.card.entity;

import gnete.card.entity.type.AmtCntType;
import gnete.card.entity.type.ChlFeeType;
import gnete.card.entity.type.CostCycleType;

import java.math.BigDecimal;
import java.util.Date;

public class ChlFee extends ChlFeeKey {
    private String feeType;

    private BigDecimal amtOrCnt;

    private String costCycle;

    private BigDecimal feeRate;

    private String curCode;

    private String modifyDate;

    private String updateBy;

    private Date updateTime;
    
    private String branchName;
    
    private BigDecimal newUlMoney;

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public BigDecimal getAmtOrCnt() {
        return amtOrCnt;
    }

    public void setAmtOrCnt(BigDecimal amtOrCnt) {
        this.amtOrCnt = amtOrCnt;
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

    public String getCurCode() {
        return curCode;
    }

    public void setCurCode(String curCode) {
        this.curCode = curCode;
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
	
	public String getAmtOrCntName() {
		return AmtCntType.ALL.get(this.amtOrCnt.toString()) == null ? "" : AmtCntType.valueOf(this.amtOrCnt.toString()).getName();
	}
	
	public String getFeeTypeName() {
		return ChlFeeType.ALL.get(this.feeType) == null ? "" : ChlFeeType.valueOf(this.feeType).getName();
	}
	
	public String getCostCycleName() {
		return CostCycleType.ALL.get(this.costCycle) == null ? "" : CostCycleType.valueOf(this.costCycle).getName();
	}

	public BigDecimal getNewUlMoney() {
		return newUlMoney;
	}

	public void setNewUlMoney(BigDecimal newUlMoney) {
		this.newUlMoney = newUlMoney;
	}
}