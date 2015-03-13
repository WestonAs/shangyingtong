package gnete.card.entity;

import java.math.BigDecimal;

public class IssuerSubTransDtotal extends IssuerSubTransDtotalKey {

	private String subTransType;
	private BigDecimal transAmt;
	private BigDecimal settAmt;
	private String chlCode;
	private BigDecimal issuerCouponAmt;
	private BigDecimal merchCouponAmt;
	private BigDecimal issuerPointSettAmt;
	private BigDecimal merchPointSettAmt;
	private Long transCnt;

	public String getSubTransType() {
		return subTransType;
	}

	public void setSubTransType(String subTransType) {
		this.subTransType = subTransType;
	}

	public BigDecimal getTransAmt() {
		return transAmt;
	}

	public void setTransAmt(BigDecimal transAmt) {
		this.transAmt = transAmt;
	}

	public BigDecimal getSettAmt() {
		return settAmt;
	}

	public void setSettAmt(BigDecimal settAmt) {
		this.settAmt = settAmt;
	}

	public String getChlCode() {
		return chlCode;
	}

	public void setChlCode(String chlCode) {
		this.chlCode = chlCode;
	}

	public BigDecimal getIssuerCouponAmt() {
		return issuerCouponAmt;
	}

	public void setIssuerCouponAmt(BigDecimal issuerCouponAmt) {
		this.issuerCouponAmt = issuerCouponAmt;
	}

	public BigDecimal getMerchCouponAmt() {
		return merchCouponAmt;
	}

	public void setMerchCouponAmt(BigDecimal merchCouponAmt) {
		this.merchCouponAmt = merchCouponAmt;
	}

	public BigDecimal getIssuerPointSettAmt() {
		return issuerPointSettAmt;
	}

	public void setIssuerPointSettAmt(BigDecimal issuerPointSettAmt) {
		this.issuerPointSettAmt = issuerPointSettAmt;
	}

	public BigDecimal getMerchPointSettAmt() {
		return merchPointSettAmt;
	}

	public void setMerchPointSettAmt(BigDecimal merchPointSettAmt) {
		this.merchPointSettAmt = merchPointSettAmt;
	}

	public Long getTransCnt() {
		return transCnt;
	}

	public void setTransCnt(Long transCnt) {
		this.transCnt = transCnt;
	}
}