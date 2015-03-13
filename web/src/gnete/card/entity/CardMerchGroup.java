package gnete.card.entity;

import gnete.card.entity.state.CardMerchGroupState;
import gnete.card.entity.type.FeeType;

import java.util.Date;

public class CardMerchGroup extends CardMerchGroupKey {
	private String groupName;

	private String merchId;

	private String feeType;

	private String status;

	private String updateBy;

	private Date updateTime;

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getMerchId() {
		return merchId;
	}

	public void setMerchId(String merchId) {
		this.merchId = merchId;
	}

	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
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
	 */
	public String getStatusName() {
		return CardMerchGroupState.ALL.get(status) == null ? ""
				: CardMerchGroupState.valueOf(status).getName();
	}

	/**
	 * 计费方式名
	 */
	public String getFeeTypeName() {
		return FeeType.ALL.get(feeType) == null ? "" : FeeType.valueOf(feeType)
				.getName();
	}
}