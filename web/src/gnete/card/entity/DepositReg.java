package gnete.card.entity;

import gnete.card.entity.flag.DepositCancelFlag;
import gnete.card.entity.state.RegisterState;
import gnete.card.entity.type.DepositType;
import gnete.card.entity.type.RebateRuleCalType;
import gnete.card.entity.type.RebateType;

import java.math.BigDecimal;
import java.util.Date;

public class DepositReg {
    private Long depositBatchId;

	private String cardId;

	private String cardClass;

	private Long cardCustomerId;

	private String rebateType;

	private Long rebateId;

	private String calType;

	private String custName;

	private BigDecimal amt;

	private BigDecimal rebateAmt;

	private BigDecimal realAmt;

	private Long referTransId;
	
	private String status;

	private String updateUser;

	private Date updateTime;

	private String remark;
	
	private String depositType;
	
	private String rebateCard;
	
	private String depositBranch;
	
	private String fromPage;
	
	private String preDeposit;
	
	private String activateBranch;
	
	private String entryUserId;
	
	private String signature;
	
	private String randomSessionId;
	
	private String cardBranch;
	
	private String fileDeposit;
	
	/** 充值是否撤销 */
	private String cancelFlag; 
	
	/** 充值撤销 */
	private String depositCancel;
	
	/** 原充值批次ID */
	private Long oldDepositBatch;
	
	/** 充值/撤销日期 */
	private String depositDate;
	
	/** 充值手续费 */
	private BigDecimal feeAmt;
	
	/** 手续费比例 */
	private BigDecimal feeRate;

	/** 起始卡号 */
	private String strCardId; 
	
	/** 结束卡号 */
	private String endCardId; 

	// 非表中字段
	/** 添加非表字段-卡类型名 */
	private String cardClassName;
	
	/** 添加非表字段-购卡客户名 */
	private String cardCustomerName;
	
	/** 添加非表字段-返利规则名 */
	private String rebateName;
	
	/**
	 *  是否手动
	 */
	private boolean manual;

	
	/**
	 *  获得‘状态’名
	 * @return
	 */
	public String getStatusName(){
		return RegisterState.ALL.get(this.status) == null ? "" : RegisterState.valueOf(this.status).getName();
	}
	
	/**
	 *  获得‘返利方式’名
	 * @return
	 */
	public String getRebateTypeName(){
		return RebateType.ALL.get(this.rebateType) == null ? "" : RebateType.valueOf(this.rebateType).getName();
	}
	
	/**
	 * 获得‘计算方式’名
	 * @return
	 */
	public String getCalTypeName(){
		return RebateRuleCalType.ALL.get(this.calType) == null ? "" : RebateRuleCalType.valueOf(this.calType).getName();
	}

	/**
	 *  获得‘充值类型’名
	 * @return
	 */
	public String getDepositTypeName(){
		return DepositType.ALL.get(this.depositType) == null ? "" : DepositType.valueOf(this.depositType).getName();
	}
	
	public String getDepositCancelName() {
		return DepositCancelFlag.ALL.get(this.depositCancel) == null ? "" : DepositCancelFlag.valueOf(this.depositCancel).getName();
	}
	
	/** 是否 充值次卡子账户 */
	public boolean isDepositTimes(){
		return DepositType.TIMES.getValue().equals(this.depositType);
	}
	
	/*
	 * ================================= getters and setters following ============================
	 */
	
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

	public Long getCardCustomerId() {
		return cardCustomerId;
	}

	public void setCardCustomerId(Long cardCustomerId) {
		this.cardCustomerId = cardCustomerId;
	}

	public String getRebateType() {
		return rebateType;
	}

	public void setRebateType(String rebateType) {
		this.rebateType = rebateType;
	}

	public Long getRebateId() {
		return rebateId;
	}

	public void setRebateId(Long rebateId) {
		this.rebateId = rebateId;
	}

	public String getCalType() {
		return calType;
	}

	public void setCalType(String calType) {
		this.calType = calType;
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
    

	public String getCardCustomerName() {
		return cardCustomerName;
	}

	public void setCardCustomerName(String cardCustomerName) {
		this.cardCustomerName = cardCustomerName;
	}

	public String getRebateName() {
		return rebateName;
	}

	public void setRebateName(String rebateName) {
		this.rebateName = rebateName;
	}
    
	public String getCardClassName() {
		return cardClassName;
	}
	
	public void setCardClassName(String cardClassName) {
		this.cardClassName = cardClassName;
	}
	
	public String getDepositType() {
		return depositType;
	}
	
	public void setDepositType(String depositType) {
		this.depositType = depositType;
	}
	
	public Long getReferTransId() {
		return referTransId;
	}
	
	public void setReferTransId(Long referTransId) {
		this.referTransId = referTransId;
	}
	
	public String getRebateCard() {
		return rebateCard;
	}

	public void setRebateCard(String rebateCard) {
		this.rebateCard = rebateCard;
	}

	public String getDepositBranch() {
		return depositBranch;
	}

	public void setDepositBranch(String depositBranch) {
		this.depositBranch = depositBranch;
	}

	public boolean isManual() {
		return manual;
	}

	public void setManual(boolean manual) {
		this.manual = manual;
	}

	public String getFromPage() {
		return fromPage;
	}

	public void setFromPage(String fromPage) {
		this.fromPage = fromPage;
	}

	public String getPreDeposit() {
		return preDeposit;
	}

	public void setPreDeposit(String preDeposit) {
		this.preDeposit = preDeposit;
	}

	public String getActivateBranch() {
		return activateBranch;
	}

	public void setActivateBranch(String activateBranch) {
		this.activateBranch = activateBranch;
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

	public String getCardBranch() {
		return cardBranch;
	}

	public void setCardBranch(String cardBranch) {
		this.cardBranch = cardBranch;
	}

	public String getFileDeposit() {
		return fileDeposit;
	}

	public void setFileDeposit(String fileDeposit) {
		this.fileDeposit = fileDeposit;
	}

	public String getCancelFlag() {
		return cancelFlag;
	}

	public void setCancelFlag(String cancelFlag) {
		this.cancelFlag = cancelFlag;
	}

	public String getDepositCancel() {
		return depositCancel;
	}

	public void setDepositCancel(String depositCancel) {
		this.depositCancel = depositCancel;
	}

	public Long getOldDepositBatch() {
		return oldDepositBatch;
	}

	public void setOldDepositBatch(Long oldDepositBatch) {
		this.oldDepositBatch = oldDepositBatch;
	}

	public String getDepositDate() {
		return depositDate;
	}

	public void setDepositDate(String depositDate) {
		this.depositDate = depositDate;
	}

	public BigDecimal getFeeAmt() {
		return feeAmt;
	}

	public void setFeeAmt(BigDecimal feeAmt) {
		this.feeAmt = feeAmt;
	}

	public BigDecimal getFeeRate() {
		return feeRate;
	}

	public void setFeeRate(BigDecimal feeRate) {
		this.feeRate = feeRate;
	}

	public String getStrCardId() {
		return strCardId;
	}

	public void setStrCardId(String strCardId) {
		this.strCardId = strCardId;
	}

	public String getEndCardId() {
		return endCardId;
	}

	public void setEndCardId(String endCardId) {
		this.endCardId = endCardId;
	}
	
}