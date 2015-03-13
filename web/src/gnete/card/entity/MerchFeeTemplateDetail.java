package gnete.card.entity;

import gnete.card.entity.type.CardMerchFeeFeeType;
import gnete.card.entity.type.CostCycleType;

import java.math.BigDecimal;
import java.util.Date;

public class MerchFeeTemplateDetail extends MerchFeeTemplateDetailKey {
    private String feeType;

    private String merchFlag;

    private String curCode;

    private BigDecimal feeRate;

    private BigDecimal minAmt;

    private BigDecimal maxAmt;

    private String costCycle;

    private String modifyDate;

    private String updateBy;

    private Date updateTime;
    
    private String branchCode;
    
    private String branchName;
    
    private String templateName;
    
    private BigDecimal newUlMoney;

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
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

    public String getCostCycle() {
        return costCycle;
    }

    public void setCostCycle(String costCycle) {
        this.costCycle = costCycle;
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
    
    public String getFeeTypeName(){
		return CardMerchFeeFeeType.ALL.get(this.feeType) == null ? "" : CardMerchFeeFeeType
				.valueOf(this.feeType).getName();
	}
    
    public String getCostCycleName(){
    	return CostCycleType.ALL.get(this.costCycle) == null ? "" : CostCycleType
				.valueOf(this.costCycle).getName();
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public BigDecimal getNewUlMoney() {
		return newUlMoney;
	}

	public void setNewUlMoney(BigDecimal newUlMoney) {
		this.newUlMoney = newUlMoney;
	}

}