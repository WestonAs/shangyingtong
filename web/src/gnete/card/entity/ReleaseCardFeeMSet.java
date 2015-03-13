package gnete.card.entity;

import gnete.card.entity.type.ReleaseCardFeeFeeType;
import gnete.card.entity.type.ReleaseCardFeeTransType;

import java.math.BigDecimal;

public class ReleaseCardFeeMSet extends BaseFeeMSet {

	private String branchCode;

	private String feeMonth;

	private String chlCode;

	private String proxyId;

	private String feeType;

	private BigDecimal chlFeeAmt;

	private BigDecimal proxyFeeAmt;

	// 新增
	private String branchName;
	private String chlName;
	private String proxyName;

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getFeeMonth() {
		return feeMonth;
	}

	public void setFeeMonth(String feeMonth) {
		this.feeMonth = feeMonth;
	}

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

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
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

	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

	public String getFeeTypeName() {
		return ReleaseCardFeeTransType.ALL.get(this.feeType) == null ? ""
				: ReleaseCardFeeFeeType.valueOf(this.feeType).getName();
	}

}