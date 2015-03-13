package gnete.card.entity;

import gnete.card.entity.state.TransLimitTermStatus;

import java.util.Date;

public class TransLimitTerm extends TransLimitTermKey {
	private String	tranEnable;

	private String	status;

	private String	updateBy;

	private Date	updateTime;

	public TransLimitTerm() {
		super();
	}

	public TransLimitTerm(String cardIssuer, String cardBin, String merNo, String termNo) {
		super(cardIssuer, cardBin, merNo, termNo);
	}

	public boolean isTranEnable(int tranOffset) {
		return (this.tranEnable == null || this.tranEnable.length() - 1 < tranOffset) ? false
				: '1' == this.tranEnable.charAt(tranOffset);
	}

	public String getStatusName() {
		return TransLimitTermStatus.ALL.get(this.status) == null ? "" : TransLimitTermStatus.valueOf(
				this.status).getName();
	}

	@Override
	public String toString() {
		return "TransLimitTerm [" + (status != null ? "status=" + status + ", " : "")
				+ (tranEnable != null ? "tranEnable=" + tranEnable + ", " : "")
				+ (updateBy != null ? "updateBy=" + updateBy + ", " : "")
				+ (updateTime != null ? "updateTime=" + updateTime + ", " : "")
				+ (super.toString() != null ? "toString()=" + super.toString() : "") + "]";
	}

	// ------------------------------- getter and setter followed------------------------

	public String getTranEnable() {
		return tranEnable;
	}

	public void setTranEnable(String tranEnable) {
		this.tranEnable = tranEnable;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
}