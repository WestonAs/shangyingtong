package gnete.card.entity;

import gnete.card.entity.type.ChlFeeType;

import java.math.BigDecimal;
import java.util.Date;

public class CardIssuerFeeDTotal extends CardIssuerFeeDTotalKey {
    private String proxyId;

    private String feeType;

    private BigDecimal feeRate;

    private String branchCurCode;

    private Long transNum;

    private BigDecimal amount;

    private BigDecimal feeAmt;

    private BigDecimal exchRate;

    private String chlCurCode;

    private BigDecimal chlFeeAmt;

    private BigDecimal proxyFeeAmt;

    private String updateBy;

    private Date updateTime;

    private String chlName;

    private String branchName;

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

    public Long getTransNum() {
        return transNum;
    }

    public void setTransNum(Long transNum) {
        this.transNum = transNum;
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

    public String getChlCurCode() {
        return chlCurCode;
    }

    public void setChlCurCode(String chlCurCode) {
        this.chlCurCode = chlCurCode;
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

	public String getChlName() {
		return chlName;
	}

	public void setChlName(String chlName) {
		this.chlName = chlName;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	
	public String getFeeTypeName() {
		return ChlFeeType.ALL.get(this.feeType) == null ? "" : ChlFeeType
				.valueOf(this.feeType).getName();
	}

}