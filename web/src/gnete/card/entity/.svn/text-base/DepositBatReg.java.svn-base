package gnete.card.entity;

import gnete.card.entity.state.RegisterState;

import java.math.BigDecimal;
import java.util.Date;

public class DepositBatReg {
    private Long depositBatRegId;

	private Long depositBatchId;

	private String cardId;

	private String cardClass;

	private String custName;

	private BigDecimal amt;

	private BigDecimal rebateAmt;

	private BigDecimal realAmt;

	private Long referTransId;

	private String status;

	private String updateUser;

	private Date updateTime;

	private String remark;
	
	private String entryUserId;
	
	private String signature;
	
	private String randomSessionId;

	// 添加非表字段-卡类型名
	private String cardClassName;
	
	public Long getDepositBatRegId() {
		return depositBatRegId;
	}

	public void setDepositBatRegId(Long depositBatRegId) {
		this.depositBatRegId = depositBatRegId;
	}

	public Long getDepositBatchId() {
		return depositBatchId;
	}

	public void setDepositBatchId(Long depositBatchId) {
		this.depositBatchId = depositBatchId;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getCardClass() {
		return cardClass;
	}

	public void setCardClass(String cardClass) {
		this.cardClass = cardClass;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public BigDecimal getAmt() {
		return amt;
	}

	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}

	public BigDecimal getRebateAmt() {
		return rebateAmt;
	}

	public void setRebateAmt(BigDecimal rebateAmt) {
		this.rebateAmt = rebateAmt;
	}

	public BigDecimal getRealAmt() {
		return realAmt;
	}

	public void setRealAmt(BigDecimal realAmt) {
		this.realAmt = realAmt;
	}

	public Long getReferTransId() {
		return referTransId;
	}

	public void setReferTransId(Long referTransId) {
		this.referTransId = referTransId;
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

	public String getCardClassName() {
		return cardClassName;
	}

	public void setCardClassName(String cardClassName) {
		this.cardClassName = cardClassName;
	}
	
	// 获得‘状态’名
	public String getStatusName(){
		return RegisterState.ALL.get(this.status) == null ? "" : RegisterState.valueOf(this.status).getName();
	}

	public String getEntryUserId() {
		return entryUserId;
	}

	public void setEntryUserId(String entryUserId) {
		this.entryUserId = entryUserId;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getRandomSessionId() {
		return randomSessionId;
	}

	public void setRandomSessionId(String randomSessionId) {
		this.randomSessionId = randomSessionId;
	}

}