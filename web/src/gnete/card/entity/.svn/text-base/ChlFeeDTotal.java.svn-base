package gnete.card.entity;

import gnete.card.entity.type.ChlFeeType;
import java.math.BigDecimal;
import java.util.Date;

public class ChlFeeDTotal extends ChlFeeDTotalKey {
    private String feeType;

    private BigDecimal feeRate;

    private Long transNum;

    private BigDecimal amount;

    private BigDecimal feeAmt;

    private BigDecimal exchRate;

    private String chlCurCode;

    private String centCurCode;

    private BigDecimal centFeeAmt;

    private String updateBy;

    private Date updateTime;
    
    private String chlName;

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
	
	public String getFeeTypeName() {
		return ChlFeeType.ALL.get(this.feeType) == null ? "" : ChlFeeType
				.valueOf(this.feeType).getName();
	}
}