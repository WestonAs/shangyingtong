package gnete.card.entity;

import gnete.card.entity.type.CardMerchFeeFeeType;
import gnete.card.entity.type.ReleaseCardFeeFeeType;
import gnete.card.entity.type.ReleaseCardFeeTransType;

import java.math.BigDecimal;
import java.util.Date;

public class ReleaseCardFeeDTotal extends ReleaseCardFeeDTotalKey {
    private String chlCode;

    private String proxyId;

    private String feeType;

    private BigDecimal feeRate;

    private String branchCurCode;

    private BigDecimal amount;

    private BigDecimal feeAmt;

    private BigDecimal exchRate;

    private String centCurCode;

    private BigDecimal centFeeAmt;

    private BigDecimal chlFeeAmt;

    private BigDecimal proxyFeeAmt;

    private BigDecimal provFeeAmt;

    private BigDecimal manageFeeAmt;

    private BigDecimal centRecvAmt;

    private String updateBy;

    private Date updateTime;

    private BigDecimal shareAmt;
    
    private String branchName;
    
    private String merchName;
    
    private String chlName;
    
    private String proxyName;
    
    private String posProvName;
    
    private String posManageName;

    public String getChlCode() {
        return chlCode;
    }

    public void setChlCode(String chlCode) {
        this.chlCode = chlCode;
    }

    public String getProxyId() {
        return proxyId;
    }

    public void setProxyId(String proxyId) {
        this.proxyId = proxyId;
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

    public String getBranchCurCode() {
        return branchCurCode;
    }

    public void setBranchCurCode(String branchCurCode) {
        this.branchCurCode = branchCurCode;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getFeeAmt() {
        return feeAmt;
    }

    public void setFeeAmt(BigDecimal feeAmt) {
        this.feeAmt = feeAmt;
    }

    public BigDecimal getExchRate() {
        return exchRate;
    }

    public void setExchRate(BigDecimal exchRate) {
        this.exchRate = exchRate;
    }

    public String getCentCurCode() {
        return centCurCode;
    }

    public void setCentCurCode(String centCurCode) {
        this.centCurCode = centCurCode;
    }

    public BigDecimal getCentFeeAmt() {
        return centFeeAmt;
    }

    public void setCentFeeAmt(BigDecimal centFeeAmt) {
        this.centFeeAmt = centFeeAmt;
    }

    public BigDecimal getChlFeeAmt() {
        return chlFeeAmt;
    }

    public void setChlFeeAmt(BigDecimal chlFeeAmt) {
        this.chlFeeAmt = chlFeeAmt;
    }

    public BigDecimal getProxyFeeAmt() {
        return proxyFeeAmt;
    }

    public void setProxyFeeAmt(BigDecimal proxyFeeAmt) {
        this.proxyFeeAmt = proxyFeeAmt;
    }

    public BigDecimal getProvFeeAmt() {
        return provFeeAmt;
    }

    public void setProvFeeAmt(BigDecimal provFeeAmt) {
        this.provFeeAmt = provFeeAmt;
    }

    public BigDecimal getManageFeeAmt() {
        return manageFeeAmt;
    }

    public void setManageFeeAmt(BigDecimal manageFeeAmt) {
        this.manageFeeAmt = manageFeeAmt;
    }

    public BigDecimal getCentRecvAmt() {
        return centRecvAmt;
    }

    public void setCentRecvAmt(BigDecimal centRecvAmt) {
        this.centRecvAmt = centRecvAmt;
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

    public BigDecimal getShareAmt() {
        return shareAmt;
    }

    public void setShareAmt(BigDecimal shareAmt) {
        this.shareAmt = shareAmt;
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
	
	public String getFeeTypeName() {
//		return CardMerchFeeFeeType.ALL.get(this.feeType) == null ? "" : CardMerchFeeFeeType.valueOf(this.feeType).getName();
		return ReleaseCardFeeFeeType.ALL.get(this.feeType) == null ? "" : ReleaseCardFeeFeeType.valueOf(this.feeType).getName();
	}

	public String getChlName() {
		return chlName;
	}

	public void setChlName(String chlName) {
		this.chlName = chlName;
	}

	public String getProxyName() {
		return proxyName;
	}

	public void setProxyName(String proxyName) {
		this.proxyName = proxyName;
	}

	public String getPosProvName() {
		return posProvName;
	}

	public void setPosProvName(String posProvName) {
		this.posProvName = posProvName;
	}

	public String getPosManageName() {
		return posManageName;
	}

	public void setPosManageName(String posManageName) {
		this.posManageName = posManageName;
	}
	
}