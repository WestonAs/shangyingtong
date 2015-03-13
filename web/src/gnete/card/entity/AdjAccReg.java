package gnete.card.entity;

import gnete.card.entity.flag.AdjAccFlag;
import gnete.card.entity.state.GoodsState;
import gnete.card.entity.state.ProcState;
import gnete.card.entity.state.RegisterState;
import gnete.card.entity.type.PlatformType;
import gnete.card.entity.type.TransType;

import java.math.BigDecimal;
import java.util.Date;

public class AdjAccReg {
	private Long adjAccId;

	private String transSn;

	private String transType;

	private String acctId;

	private String cardId;

	private String merNo;

	private String termlId;

	private String platform;

	private BigDecimal transAmt;

	private Date procTime;

	private String procStatus;

	private String busiStatus;

	private String goodsStatus;

	private BigDecimal amt;

	private String status;

	private String updateUser;

	private Date updateTime;

	private String remark;

	private String flag;
	// 表中新增字段

	/** 发卡机构编号 */
	private String cardBranch;

	/** 发起方编号 */
	private String initiator;

	// 非表字段
	private String merchName;

	public Long getAdjAccId() {
		return adjAccId;
	}

	public void setAdjAccId(Long adjAccId) {
		this.adjAccId = adjAccId;
	}

	public String getTransSn() {
		return transSn;
	}

	public void setTransSn(String transSn) {
		this.transSn = transSn;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getAcctId() {
		return acctId;
	}

	public void setAcctId(String acctId) {
		this.acctId = acctId;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getMerNo() {
		return merNo;
	}

	public void setMerNo(String merNo) {
		this.merNo = merNo;
	}

	public String getTermlId() {
		return termlId;
	}

	public void setTermlId(String termlId) {
		this.termlId = termlId;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public BigDecimal getTransAmt() {
		return transAmt;
	}

	public void setTransAmt(BigDecimal transAmt) {
		this.transAmt = transAmt;
	}

	public Date getProcTime() {
		return procTime;
	}

	public void setProcTime(Date procTime) {
		this.procTime = procTime;
	}

	public String getProcStatus() {
		return procStatus;
	}

	public void setProcStatus(String procStatus) {
		this.procStatus = procStatus;
	}

	public String getBusiStatus() {
		return busiStatus;
	}

	public void setBusiStatus(String busiStatus) {
		this.busiStatus = busiStatus;
	}

	public String getGoodsStatus() {
		return goodsStatus;
	}

	public void setGoodsStatus(String goodsStatus) {
		this.goodsStatus = goodsStatus;
	}

	public BigDecimal getAmt() {
		return amt;
	}

	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getTransTypeName() {
		return TransType.ALL.get(this.transType) == null ? "" : TransType.valueOf(this.transType).getName();
	}

	public String getPlatformName() {
		return PlatformType.ALL.get(this.platform) == null ? "" : PlatformType.valueOf(this.platform).getName();
	}

	public String getProcStatusName() {
		return ProcState.ALL.get(this.procStatus) == null ? "" : ProcState.valueOf(this.procStatus).getName();
	}

	public String getBusiStatusName() {
		return ProcState.getBusiState().get(this.busiStatus) == null ? "" : ProcState.valueOf(this.busiStatus).getName();
	}

	public String getMerchName() {
		return merchName;
	}

	public void setMerchName(String merchName) {
		this.merchName = merchName;
	}

	public String getGoodsStatusName() {
		return GoodsState.ALL.get(this.goodsStatus) == null ? "" : GoodsState.valueOf(this.goodsStatus).getName();
	}

	public String getStatusName() {
		return RegisterState.ALL.get(this.status) == null ? "" : RegisterState.valueOf(this.status).getName();
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getFlagName() {
		return AdjAccFlag.ALL.get(this.flag) == null ? "" : AdjAccFlag.valueOf(this.flag).getName();
	}

	public String getCardBranch() {
		return cardBranch;
	}

	public void setCardBranch(String cardBranch) {
		this.cardBranch = cardBranch;
	}

	public String getInitiator() {
		return initiator;
	}

	public void setInitiator(String initiator) {
		this.initiator = initiator;
	}

}