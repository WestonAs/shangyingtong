package gnete.card.entity;

import gnete.card.entity.flag.YesOrNoFlag;
import gnete.card.entity.state.FenzhiCardBinMgrState;

import java.util.Date;

public class FenZhiCardBinMgr {
	private String cardBin;

	private String cardBinPrex;

	private String currentBranch;

	private String lastBranch;

	private String useFlag;

	private String status;

	private String updateBy;

	private Date updateTime;

	public String getCardBin() {
		return cardBin;
	}

	public void setCardBin(String cardBin) {
		this.cardBin = cardBin;
	}

	public String getCardBinPrex() {
		return cardBinPrex;
	}

	public void setCardBinPrex(String cardBinPrex) {
		this.cardBinPrex = cardBinPrex;
	}

	public String getCurrentBranch() {
		return currentBranch;
	}

	public void setCurrentBranch(String currentBranch) {
		this.currentBranch = currentBranch;
	}

	public String getLastBranch() {
		return lastBranch;
	}

	public void setLastBranch(String lastBranch) {
		this.lastBranch = lastBranch;
	}

	public String getUseFlag() {
		return useFlag;
	}

	public void setUseFlag(String useFlag) {
		this.useFlag = useFlag;
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
	 * 
	 * @return
	 */
	public String getStatusName() {
		return FenzhiCardBinMgrState.ALL.get(this.status) == null ? "" : FenzhiCardBinMgrState.valueOf(this.status).getName();
	}
	
	/**
	 * 是否
	 * @return
	 */
	public String getUseFlagName() {
		return YesOrNoFlag.ALL.get(this.useFlag) == null ? "" : YesOrNoFlag.valueOf(this.useFlag).getName();
	}
}