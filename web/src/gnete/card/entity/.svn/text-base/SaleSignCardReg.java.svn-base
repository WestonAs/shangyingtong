package gnete.card.entity;

import gnete.card.entity.state.RegisterState;
import gnete.card.entity.type.CertType;

import java.math.BigDecimal;
import java.util.Date;

public class SaleSignCardReg {
	private Long saleSignCardId;

	private String cardId;

	private Long signCustId;

	private Long signRuleId;

	private BigDecimal expenses;

	private BigDecimal overdraft;

	private String saleDate;

	private String nextBalDate;

	private String custName;

	private String certType;

	private String certNo;

	private String zip;

	private String address;

	private String phone;

	private String fax;

	private String email;

	private String status;

	private String updateUser;

	private Date updateTime;

	private String remark;

	private String branchCode;

	// 添加非表字段-签单客户名称
	private String signCustName;

	// 添加非表字段-签单规则名称
	private String signRuleName;

	// 添加非表字段-售卡机构名称
	private String branchName;

	public Long getSaleSignCardId() {
		return saleSignCardId;
	}

	public void setSaleSignCardId(Long saleSignCardId) {
		this.saleSignCardId = saleSignCardId;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public Long getSignCustId() {
		return signCustId;
	}

	public void setSignCustId(Long signCustId) {
		this.signCustId = signCustId;
	}

	public Long getSignRuleId() {
		return signRuleId;
	}

	public void setSignRuleId(Long signRuleId) {
		this.signRuleId = signRuleId;
	}

	public BigDecimal getExpenses() {
		return expenses;
	}

	public void setExpenses(BigDecimal expenses) {
		this.expenses = expenses;
	}

	public BigDecimal getOverdraft() {
		return overdraft;
	}

	public void setOverdraft(BigDecimal overdraft) {
		this.overdraft = overdraft;
	}

	public String getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(String saleDate) {
		this.saleDate = saleDate;
	}

	public String getNextBalDate() {
		return nextBalDate;
	}

	public void setNextBalDate(String nextBalDate) {
		this.nextBalDate = nextBalDate;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getCertType() {
		return certType;
	}

	public void setCertType(String certType) {
		this.certType = certType;
	}

	public String getCertNo() {
		return certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getSignCustName() {
		return signCustName;
	}

	public void setSignCustName(String signCustName) {
		this.signCustName = signCustName;
	}

	public String getSignRuleName() {
		return signRuleName;
	}

	public void setSignRuleName(String signRuleName) {
		this.signRuleName = signRuleName;
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

	/**
	 * 获得‘状态’名
	 */
	public String getStatusName() {
		return RegisterState.ALL.get(status) == null ? "" : RegisterState
				.valueOf(this.status).getName();
	}

	/**
	 * 获得‘证件类型’名
	 */
	public String getCertTypeName() {
		return CertType.ALL.get(certType) == null ? "" : CertType.valueOf(
				this.certType).getName();
	}

}