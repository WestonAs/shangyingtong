package gnete.card.entity;

import gnete.card.entity.state.VerifyCheckState;

import java.math.BigDecimal;
import java.util.Date;

public class ChlFeeMSet extends ChlFeeMSetKey {
    private String feeDate;

    private BigDecimal lastFee;

    private BigDecimal chlFeeAmt;

    private BigDecimal recvAmt;

    private BigDecimal nextFee;

    private String chkStatus;

    private String updateBy;

    private Date updateTime;

    private String curCode;

    private String chlName;

    public String getFeeDate() {
        return feeDate;
    }

    public void setFeeDate(String feeDate) {
        this.feeDate = feeDate;
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
	
	public String getChkStatusName() {
		return VerifyCheckState.ALL.get(this.chkStatus) == null ? ""
				: VerifyCheckState.valueOf(this.chkStatus).getName();
	}
}