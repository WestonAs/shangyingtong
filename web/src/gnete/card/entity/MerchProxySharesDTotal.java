package gnete.card.entity;

import java.math.BigDecimal;
import java.util.Date;

public class MerchProxySharesDTotal extends MerchProxySharesDTotalKey {
    private BigDecimal feeRate;

    private BigDecimal amount;

    private BigDecimal merchPayFee;

    private BigDecimal merchProxyFee;

    private Date updateTime;

    private String updateBy;
    
    private String curCode;
    
    private String branchName;
    
    private String proxyName;
    
    private String merchName;

    public BigDecimal getFeeRate() {
        return feeRate;
    }

    public void setFeeRate(BigDecimal feeRate) {
        this.feeRate = feeRate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getMerchPayFee() {
        return merchPayFee;
    }

    public void setMerchPayFee(BigDecimal merchPayFee) {
        this.merchPayFee = merchPayFee;
    }

    public BigDecimal getMerchProxyFee() {
        return merchProxyFee;
    }

    public void setMerchProxyFee(BigDecimal merchProxyFee) {
        this.merchProxyFee = merchProxyFee;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getProxyName() {
		return proxyName;
	}

	public void setProxyName(String proxyName) {
		this.proxyName = proxyName;
	}

	public String getMerchName() {
		return merchName;
	}

	public void setMerchName(String merchName) {
		this.merchName = merchName;
	}

	public String getCurCode() {
		return curCode;
	}

	public void setCurCode(String curCode) {
		this.curCode = curCode;
	}
}