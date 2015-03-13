package gnete.card.entity;

import gnete.card.entity.state.RegisterState;
import gnete.card.entity.type.PresellType;

import java.math.BigDecimal;
import java.util.Date;

public class SaleCardBatReg {
    private Long saleCardBatRegId;

	private Long saleBatchId;

	private String cardId;

	private String cardClass;

	private String cardSubClass;

	private String custName;

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
	
	private String cardBranch;

	public Long getSaleCardBatRegId() {
		return saleCardBatRegId;
	}

	public void setSaleCardBatRegId(Long saleCardBatRegId) {
		this.saleCardBatRegId = saleCardBatRegId;
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

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
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

    // 获得‘状态’名
    public String getStatusName(){
    	return RegisterState.ALL.get(this.status) == null ? "" : RegisterState.valueOf(this.status).getName();
    }

    // 获得‘预售类型’名
    public String getPresellName(){
    	return PresellType.ALL.get(this.presell) == null ? "" : PresellType.valueOf(this.presell).getName();
    }

	public String getCardBranch() {
		return cardBranch;
	}

	public void setCardBranch(String cardBranch) {
		this.cardBranch = cardBranch;
	}

}