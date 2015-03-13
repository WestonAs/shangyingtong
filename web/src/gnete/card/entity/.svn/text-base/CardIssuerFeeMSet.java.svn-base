package gnete.card.entity;

import gnete.card.entity.state.VerifyCheckState;

import java.math.BigDecimal;
import java.util.Date;

public class CardIssuerFeeMSet extends CardIssuerFeeMSetKey {
    private String feeDate;

    private String proxyId;

    private BigDecimal proxyFeeAmt;

    private BigDecimal lastFee;

    private BigDecimal chlFeeAmt;

    private BigDecimal recvAmt;

    private BigDecimal nextFee;

    private String chkStatus;

    private String updateBy;

    private Date updateTime;

    private String curCode;
    
    private String chlName;
    
    private String branchName;

    public String getFeeDate() {
        return feeDate;
    }

    public void setFeeDate(String feeDate) {
        this.feeDate = feeDate;
    }

    public String getProxyId() {
        return proxyId;
    }

    public void setProxyId(String proxyId) {
        this.proxyId = proxyId;
    }

    public BigDecimal getProxyFeeAmt() {
        return proxyFeeAmt;
    }

    public void setProxyFeeAmt(BigDecimal proxyFeeAmt) {
        this.proxyFeeAmt = proxyFeeAmt;
    }

    public BigDecimal getLastFee() {
        return lastFee;
    }

    public void setLastFee(BigDecimal lastFee) {
        this.lastFee = lastFee;
    }

    public BigDecimal getChlFeeAmt() {
        return chlFeeAmt;
    }

    public void setChlFeeAmt(BigDecimal chlFeeAmt) {
        this.chlFeeAmt = chlFeeAmt;
    }

    public BigDecimal getRecvAmt() {
        return recvAmt;
    }

    public void setRecvAmt(BigDecimal recvAmt) {
        this.recvAmt = recvAmt;
    }

    public BigDecimal getNextFee() {
        return nextFee;
    }

    public void setNextFee(BigDecimal nextFee) {
        this.nextFee = nextFee;
    }

    public String getChkStatus() {
        return chkStatus;
    }

    public void setChkStatus(String chkStatus) {
        this.chkStatus = chkStatus;
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

    public String getCurCode() {
        return curCode;
    }

    public void setCurCode(String curCode) {
        this.curCode = curCode;
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
	
	public String getChkStatusName() {
		return VerifyCheckState.ALL.get(this.chkStatus) == null ? ""
				: VerifyCheckState.valueOf(this.chkStatus).getName();
	}
}