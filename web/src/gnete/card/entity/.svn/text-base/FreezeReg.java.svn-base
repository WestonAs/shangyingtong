package gnete.card.entity;

import gnete.card.entity.state.RegisterState;
import gnete.card.entity.type.SubacctType;

import java.math.BigDecimal;
import java.util.Date;

public class FreezeReg {
	private Long freezeId;

	private String cardId;

	private String acctId;

	private String subacctType;

	private BigDecimal avlbBal;

	private BigDecimal fznAmt;

	private BigDecimal newFznAmt;

	private String status;

	private String updateUser;

	private Date updateTime;

	private String remark;

	private String branchCode;

	private String branchName;

	private String startCard;

	private String endCard;

	private Long cardNum;

	private String procNote;

	public Long getFreezeId() {
		return freezeId;
	}

	public void setFreezeId(Long freezeId) {
		this.freezeId = freezeId;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getAcctId() {
		return acctId;
	}

	public void setAcctId(String acctId) {
		this.acctId = acctId;
	}

	public String getSubacctType() {
		return subacctType;
	}

	public void setSubacctType(String subacctType) {
		this.subacctType = subacctType;
	}

	public BigDecimal getAvlbBal() {
		return avlbBal;
	}

	public void setAvlbBal(BigDecimal avlbBal) {
		this.avlbBal = avlbBal;
	}

	public BigDecimal getFznAmt() {
		return fznAmt;
	}

	public void setFznAmt(BigDecimal fznAmt) {
		this.fznAmt = fznAmt;
	}

	public BigDecimal getNewFznAmt() {
		return newFznAmt;
	}

	public void setNewFznAmt(BigDecimal newFznAmt) {
		this.newFznAmt = newFznAmt;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSubacctTypeName() {
		return SubacctType.ALL.get(this.subacctType) == null ? "" : SubacctType.valueOf(this.subacctType).getName();
	}

	public String getStatusName() {
		return RegisterState.ALL.get(this.status) == null ? "" : RegisterState.valueOf(this.status).getName();
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getStartCard() {
		return startCard;
	}

	public void setStartCard(String startCard) {
		this.startCard = startCard;
	}

	public String getEndCard() {
		return endCard;
	}

	public void setEndCard(String endCard) {
		this.endCard = endCard;
	}

	public Long getCardNum() {
		return cardNum;
	}

	public void setCardNum(Long cardNum) {
		this.cardNum = cardNum;
	}

	public String getProcNote() {
		return procNote;
	}

	public void setProcNote(String procNote) {
		this.procNote = procNote;
	}
}