package gnete.card.entity;

import gnete.card.entity.state.CheckState;

import java.util.Date;

public class CardBinPreReg {
	private String id;

	private String cardBinPrex;

	private String branchCode;

	private String status;

	private String updateBy;

	private Date updateTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCardBinPrex() {
		return cardBinPrex;
	}

	public void setCardBinPrex(String cardBinPrex) {
		this.cardBinPrex = cardBinPrex;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
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

	/**
	 * 状态名
	 * @return
	 */
	public String getStatusName() {
		return CheckState.ALL.get(this.status) == null ? "" : CheckState
				.valueOf(this.status).getName();
	}
}