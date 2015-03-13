package gnete.card.entity;

import gnete.card.entity.type.TransType;

public class TransDtotalKey {
	private String cardIssuer;

	private String curCode;

	private String merchId;

	private String procStatus;

	private String transType;

	private String workdate;

	public String getCardIssuer() {
		return cardIssuer;
	}

	public void setCardIssuer(String cardIssuer) {
		this.cardIssuer = cardIssuer;
	}

	public String getCurCode() {
		return curCode;
	}

	public void setCurCode(String curCode) {
		this.curCode = curCode;
	}

	public String getMerchId() {
		return merchId;
	}

	public void setMerchId(String merchId) {
		this.merchId = merchId;
	}

	public String getProcStatus() {
		return procStatus;
	}

	public void setProcStatus(String procStatus) {
		this.procStatus = procStatus;
	}

	public String getTransType() {
		return transType;
	}

	public String getTransTypeName() {
		return TransType.ALL.get(this.transType) == null ? "" : TransType.valueOf(this.transType).getName();
	}
	
	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getWorkdate() {
		return workdate;
	}

	public void setWorkdate(String workdate) {
		this.workdate = workdate;
	}
}