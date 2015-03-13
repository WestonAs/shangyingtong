package gnete.card.entity;

import gnete.card.entity.state.VerifyCheckState;
import java.math.BigDecimal;
import java.util.Date;

public class BaseMSet {
	// 日期或月份
	private String feeDate;
	// 上期结转
	private BigDecimal lastFee;
	// 本期金额，交易金额, 返利金额
	private BigDecimal feeAmt;
	// 实收/分润 金额
	private BigDecimal recvAmt;
	// 下期结转
	private BigDecimal nextFee;
	// 核销状态
	private String chkStatus;
	// 货币代码
	private String curCode;

	private String updateBy;

	private Date updateTime;

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

	public BigDecimal getFeeAmt() {
		return feeAmt;
	}

	public void setFeeAmt(BigDecimal feeAmt) {
		this.feeAmt = feeAmt;
	}

	public String getCurCode() {
		return curCode;
	}

	public void setCurCode(String curCode) {
		this.curCode = curCode;
	}
	
	/**
	 * 状态名
	 */
	public String getChkStatusName() {
		return VerifyCheckState.ALL.get(this.chkStatus) == null ? ""
				: VerifyCheckState.valueOf(this.chkStatus).getName();
	}
}