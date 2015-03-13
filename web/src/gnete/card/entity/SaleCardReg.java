package gnete.card.entity;

import gnete.card.entity.flag.SaleCancelFlag;
import gnete.card.entity.flag.YesOrNoFlag;
import gnete.card.entity.state.RegisterState;
import gnete.card.entity.type.CardType;
import gnete.card.entity.type.DepositType;
import gnete.card.entity.type.PresellType;
import gnete.card.entity.type.RebateType;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class SaleCardReg {

	private Long saleBatchId;

	private String cardId;

	private String cardClass;

	private String cardSubClass;

	private Long cardCustomerId;

	private String custName;

	private Long rebateId;

	private BigDecimal expenses;

	private BigDecimal amt;

	private BigDecimal rebateAmt;

	private BigDecimal realAmt;

	private BigDecimal overdraft;

	private String presell;

	private Long referTransId;

	private String status;

	private String updateUser;

	private Date updateTime;

	private String remark;

	private String branchCode;

	private String depositType;

	private String activateBranch;

	private String rebateCard;

	private String rebateType;

	private String entryUserid;

	private String signature;

	private String randomSessionId;

	private String cardBranch;

	private String cancelFlag; // 售卡是否撤销

	private String saleCancel; // 售卡撤销标志

	private Long oldSaleBatch;// 原售卡批次ID

	private String saleDate;// 售卡时的系统工作日期
	
	private String strCardId; // 起始卡号
	
	private String endCardId; // 结束卡号
	
	// 表中新加字段
	/** 付款账号 */
	private String payAccNo;
	
	/** 付款户名 */
	private String payAccName;
	
	/** 售卡手续费 */
	private BigDecimal feeAmt;
	
	/** 手续费比例 */
	private BigDecimal feeRate;

	private String couponClass;
	
	private BigDecimal couponAmt;

	// 非表字段
	// 是否指定了手工返利
	private boolean manual;

	// 添加非表字段-售卡机构名称
	private String branchName;

	private String cardCustomerName;

	/** 
	 * 是否 单张 售卡/预售卡  （即 cardID不为空）
	 */
	public boolean isSingleCardSale() {
		return StringUtils.isNotBlank(this.getCardId());
	}
	/** 
	 * 是否 预售卡记录 
	 */
	public boolean isPreSellReg(){
		return PresellType.PRESELL.getValue().equals(getPresell());
	}
	
	
	public String getEndCardId() {
		return endCardId;
	}

	public void setEndCardId(String endCardId) {
		this.endCardId = endCardId;
	}

	public String getStrCardId() {
		return strCardId;
	}

	public void setStrCardId(String strCardId) {
		this.strCardId = strCardId;
	}

	public Long getSaleBatchId() {
		return saleBatchId;
	}

	public void setSaleBatchId(Long saleBatchId) {
		this.saleBatchId = saleBatchId;
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

	public String getCardSubClass() {
		return cardSubClass;
	}

	public void setCardSubClass(String cardSubClass) {
		this.cardSubClass = cardSubClass;
	}

	public Long getCardCustomerId() {
		return cardCustomerId;
	}

	public void setCardCustomerId(Long cardCustomerId) {
		this.cardCustomerId = cardCustomerId;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public Long getRebateId() {
		return rebateId;
	}

	public void setRebateId(Long rebateId) {
		this.rebateId = rebateId;
	}

	public BigDecimal getExpenses() {
		return expenses;
	}

	public void setExpenses(BigDecimal expenses) {
		this.expenses = expenses;
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

	public BigDecimal getOverdraft() {
		return overdraft;
	}

	public void setOverdraft(BigDecimal overdraft) {
		this.overdraft = overdraft;
	}

	public String getPresell() {
		return presell;
	}

	public void setPresell(String presell) {
		this.presell = presell;
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

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getDepositType() {
		return depositType;
	}

	public void setDepositType(String depositType) {
		this.depositType = depositType;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public Long getReferTransId() {
		return referTransId;
	}

	public void setReferTransId(Long referTransId) {
		this.referTransId = referTransId;
	}

	public String getActivateBranch() {
		return activateBranch;
	}

	public void setActivateBranch(String activateBranch) {
		this.activateBranch = activateBranch;
	}

	public boolean isManual() {
		return manual;
	}

	public void setManual(boolean manual) {
		this.manual = manual;
	}

	public String getRebateCard() {
		return rebateCard;
	}

	public void setRebateCard(String rebateCard) {
		this.rebateCard = rebateCard;
	}

	public String getRebateType() {
		return rebateType;
	}

	public void setRebateType(String rebateType) {
		this.rebateType = rebateType;
	}

	public String getCardCustomerName() {
		return cardCustomerName;
	}

	public void setCardCustomerName(String cardCustomerName) {
		this.cardCustomerName = cardCustomerName;
	}

	public String getEntryUserid() {
		return entryUserid;
	}

	public void setEntryUserid(String entryUserid) {
		this.entryUserid = entryUserid;
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

	public String getCancelFlag() {
		return cancelFlag;
	}

	public void setCancelFlag(String cancelFlag) {
		this.cancelFlag = cancelFlag;
	}

	public String getSaleCancel() {
		return saleCancel;
	}

	public void setSaleCancel(String saleCancel) {
		this.saleCancel = saleCancel;
	}

	public Long getOldSaleBatch() {
		return oldSaleBatch;
	}

	public void setOldSaleBatch(Long oldSaleBatch) {
		this.oldSaleBatch = oldSaleBatch;
	}

	public String getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(String saleDate) {
		this.saleDate = saleDate;
	}

	// 获得‘状态’名
	public String getStatusName() {
		return RegisterState.ALL.get(this.status) == null ? "" : RegisterState
				.valueOf(this.status).getName();
	}

	// 获得‘预售类型’名
	public String getPresellName() {
		return PresellType.ALL.get(this.presell) == null ? "" : PresellType
				.valueOf(this.presell).getName();
	}

	// 获得‘充值类型’名
	public String getDepositTypeName() {
		return DepositType.ALL.get(this.depositType) == null ? "" : DepositType
				.valueOf(this.depositType).getName();
	}

	/**
	 * 获得返利类型名
	 * 
	 * @return
	 */
	public String getRebateTypeName() {
		return RebateType.ALL.get(this.rebateType) == null ? "" : RebateType
				.valueOf(this.rebateType).getName();
	}

	/**
	 * 获得卡类型名
	 * 
	 * @return
	 */
	public String getCardClassName() {
		return CardType.ALL.get(this.cardClass) == null ? "" : CardType
				.valueOf(this.cardClass).getName();
	}

	/**
	 * 取得售卡撤销标志名
	 * 
	 * @return
	 */
	public String getSaleCancelName() {
		return SaleCancelFlag.ALL.get(this.saleCancel) == null ? ""
				: SaleCancelFlag.valueOf(this.saleCancel).getName();
	}

	/**
	 * 是否是售卡撤销
	 * 
	 * @return
	 */
	public String getCancelFlagName() {
		return YesOrNoFlag.ALL.get(this.cancelFlag) == null ? "" : YesOrNoFlag
				.valueOf(this.cancelFlag).getName();
	}

	public String getPayAccNo() {
		return payAccNo;
	}

	public void setPayAccNo(String payAccNo) {
		this.payAccNo = payAccNo;
	}

	public String getPayAccName() {
		return payAccName;
	}

	public void setPayAccName(String payAccName) {
		this.payAccName = payAccName;
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
	public String getCouponClass() {
		return couponClass;
	}
	public void setCouponClass(String couponClass) {
		this.couponClass = couponClass;
	}
	public BigDecimal getCouponAmt() {
		return couponAmt;
	}
	public void setCouponAmt(BigDecimal couponAmt) {
		this.couponAmt = couponAmt;
	}
	
}