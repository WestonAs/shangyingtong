package gnete.card.entity;

import gnete.card.entity.state.CustomerRebateState;
import gnete.card.entity.type.CustomerRebateType;
import gnete.card.entity.type.RebateRuleCalType;

import java.util.Date;

public class CustomerRebateReg {
	private Long customerRebateRegId;

	private Long cardCustomerId;

	private String binNo;

	private Long saleRebateId;

	private Long depositRebateId;

	private String status;

	private String updateUser;

	private Date updateTime;

	private String cardBranch;
	
	private String inputBranch;

	// 添加非表字段-购卡客户名称
	private String cardCustomerName;

	// 添加非表字段-卡BIN名称
	private String binName;

	// 添加非表字段-售卡返利规则名称
	private String saleRebateName;

	// 添加非表字段-充值返利规则名称
	private String depositRebateName;

	// 添加非表字段-返利方式
	private String rebateType;

	// 添加非表字段-售卡计算方式
	private String saleCalType;

	// 添加非表字段-充值计算方式
	private String depositCalType;

	// 添加非表字段-卡类型
	private String cardType;

	// 添加非表字段-卡类型名称
	private String cardTypeName;

	public Long getCardCustomerId() {
		return cardCustomerId;
	}

	public void setCardCustomerId(Long cardCustomerId) {
		this.cardCustomerId = cardCustomerId;
	}

	public String getBinNo() {
		return binNo;
	}

	public void setBinNo(String binNo) {
		this.binNo = binNo;
	}

	public Long getSaleRebateId() {
		return saleRebateId;
	}

	public void setSaleRebateId(Long saleRebateId) {
		this.saleRebateId = saleRebateId;
	}

	public Long getDepositRebateId() {
		return depositRebateId;
	}

	public void setDepositRebateId(Long depositRebateId) {
		this.depositRebateId = depositRebateId;
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

	public String getCardBranch() {
		return cardBranch;
	}

	public void setCardBranch(String cardBranch) {
		this.cardBranch = cardBranch;
	}

	public String getCardCustomerName() {
		return cardCustomerName;
	}

	public void setCardCustomerName(String cardCustomerName) {
		this.cardCustomerName = cardCustomerName;
	}

	public String getBinName() {
		return binName;
	}

	public void setBinName(String binName) {
		this.binName = binName;
	}

	public String getSaleRebateName() {
		return saleRebateName;
	}

	public void setSaleRebateName(String saleRebateName) {
		this.saleRebateName = saleRebateName;
	}

	public String getDepositRebateName() {
		return depositRebateName;
	}

	public void setDepositRebateName(String depositRebateName) {
		this.depositRebateName = depositRebateName;
	}

	public Long getCustomerRebateRegId() {
		return customerRebateRegId;
	}

	public void setCustomerRebateRegId(Long customerRebateRegId) {
		this.customerRebateRegId = customerRebateRegId;
	}

	public String getRebateType() {
		return rebateType;
	}

	public void setRebateType(String rebateType) {
		this.rebateType = rebateType;
	}

	public String getSaleCalType() {
		return saleCalType;
	}

	public void setSaleCalType(String saleCalType) {
		this.saleCalType = saleCalType;
	}

	public String getDepositCalType() {
		return depositCalType;
	}

	public void setDepositCalType(String depositCalType) {
		this.depositCalType = depositCalType;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardTypeName() {
		return cardTypeName;
	}

	public void setCardTypeName(String cardTypeName) {
		this.cardTypeName = cardTypeName;
	}

	/**
	 * 获取“状态”名称
	 */
	public String getStatusName() {
		return CustomerRebateState.ALL.get(this.status) == null ? ""
				: CustomerRebateState.valueOf(this.status).getName();
	}

	/**
	 * 获取“返利方式”名称
	 */
	public String getRebateTypeName() {
		return CustomerRebateType.ALL.get(this.rebateType) == null ? ""
				: CustomerRebateType.valueOf(this.rebateType).getName();
	}

	/**
	 * 获取“售卡计算方式”名称
	 */
	public String getSaleCalTypeName() {
		return RebateRuleCalType.ALL.get(this.saleCalType) == null ? ""
				: RebateRuleCalType.valueOf(this.saleCalType).getName();
	}

	/**
	 * 获取“充值计算方式”名称
	 */
	public String getDepositCalTypeName() {
		return RebateRuleCalType.ALL.get(this.depositCalType) == null ? ""
				: RebateRuleCalType.valueOf(this.depositCalType).getName();
	}

	public String getInputBranch() {
		return inputBranch;
	}

	public void setInputBranch(String inputBranch) {
		this.inputBranch = inputBranch;
	}

}