package gnete.card.entity;

import gnete.card.entity.flag.GroupFlag;
import gnete.card.entity.state.FeeStatus;
import gnete.card.entity.type.CostCycleType;
import gnete.card.entity.type.FeeCycleType;
import gnete.card.entity.type.FeePrincipleType;
import gnete.card.entity.type.ReleaseCardFeeFeeType;
import gnete.card.entity.type.ReleaseCardFeeTransType;
import java.math.BigDecimal;
import java.util.Date;

public class ReleaseCardFee extends ReleaseCardFeeKey {
    private String feeType;

    private String costCycle;

    private BigDecimal feeRate;

    private String modifyDate;

    private String updateBy;

    private Date updateTime;

    private String merchFlag;

    private String curCode;

    private String feeMode;

    private BigDecimal amtOrCnt;

    private String groupFlag;

    private String chlCode;

    private BigDecimal minAmt;

    private BigDecimal maxAmt;

    private String feeCycleType;

    private String adjustCycleType;

    private Short adjustMonths;

    private String feePrinciple;

    private String effDate;

    private String expirDate;

    private String status;

    private Long feeRuleId;

    private String feeDate;
    
    private String branchName;
	
	private String merchName;
	
	private String chlName;
	
	private BigDecimal newUlMoney;

	public String getFeeTypeName() {
		return ReleaseCardFeeFeeType.ALL.get(this.feeType) == null ? "" : ReleaseCardFeeFeeType.valueOf(this.feeType).getName();
	}

	public String getTransTypeName() {
		return ReleaseCardFeeTransType.ALL.get(this.transType) == null ? "" : ReleaseCardFeeTransType.valueOf(this.transType).getName();
	}
	
	public String getFeeCycleTypeName() {
		return FeeCycleType.ALL.get(this.feeCycleType) == null ? "" : FeeCycleType.valueOf(this.feeCycleType).getName();
	}

	public String getAdjustCycleTypeName() {
		return FeeCycleType.ALL.get(this.adjustCycleType) == null ? "" : FeeCycleType.valueOf(this.adjustCycleType).getName();
	}

	public String getFeePrincipleName() {
		return FeePrincipleType.ALL.get(this.feePrinciple) == null ? "" : FeePrincipleType.valueOf(this.feePrinciple).getName();
	}
	
	public String getGroupFlagName() {
		return GroupFlag.ALL.get(this.groupFlag) == null ? "" : GroupFlag.valueOf(this.groupFlag).getName();
	}
	
	public String getCostCycleName() {
		return CostCycleType.ALL.get(this.costCycle) == null ? "" : CostCycleType.valueOf(this.costCycle).getName();
	}

	public String getStatusName() {
		return FeeStatus.ALL.get(this.status) == null ? "" : FeeStatus.valueOf(this.status).getName();
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

    public String getFeeMode() {
        return feeMode;
    }

    public void setFeeMode(String feeMode) {
        this.feeMode = feeMode;
    }

    public BigDecimal getAmtOrCnt() {
        return amtOrCnt;
    }

    public void setAmtOrCnt(BigDecimal amtOrCnt) {
        this.amtOrCnt = amtOrCnt;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getFeeRuleId() {
        return feeRuleId;
    }

    public void setFeeRuleId(Long feeRuleId) {
        this.feeRuleId = feeRuleId;
    }

    public String getFeeDate() {
        return feeDate;
    }

    public void setFeeDate(String feeDate) {
        this.feeDate = feeDate;
    }

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getMerchName() {
		return merchName;
	}

	public void setMerchName(String merchName) {
		this.merchName = merchName;
	}

	public String getChlName() {
		return chlName;
	}

	public void setChlName(String chlName) {
		this.chlName = chlName;
	}

	public BigDecimal getNewUlMoney() {
		return newUlMoney;
	}

	public void setNewUlMoney(BigDecimal newUlMoney) {
		this.newUlMoney = newUlMoney;
	}
}