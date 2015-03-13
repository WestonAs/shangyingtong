package gnete.card.entity;

import gnete.card.entity.state.CommonState;
import gnete.card.entity.type.CertType;
import gnete.card.entity.type.RebateType;

import java.math.BigDecimal;
import java.util.Date;

public class SignCust {
	private Long signCustId;

	private String signCustName;

	private String groupCardId;

	private String rebateType;

	private String contact;

	private String credType;

	private String credNo;

	private String zip;

	private String address;

	private String phone;

	private String mobile;

	private String fax;

	private String email;

	private String status;

	private String bank;

	private String bankNo;

	private String bankAccNo;

	private BigDecimal overdraftSum;

	private BigDecimal overdraftBal;

	private String updateUser;

	private Date updateTime;

	private String billDate;

	private long payDay;

	private String cardBranch;

	public Long getSignCustId() {
		return signCustId;
	}

	public void setSignCustId(Long signCustId) {
		this.signCustId = signCustId;
	}

	public String getSignCustName() {
		return signCustName;
	}

	public void setSignCustName(String signCustName) {
		this.signCustName = signCustName;
	}

	public String getGroupCardId() {
		return groupCardId;
	}

	public void setGroupCardId(String groupCardId) {
		this.groupCardId = groupCardId;
	}

	public String getRebateType() {
		return rebateType;
	}

	public void setRebateType(String rebateType) {
		this.rebateType = rebateType;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getCredType() {
		return credType;
	}

	public void setCredType(String credType) {
		this.credType = credType;
	}

	public String getCredNo() {
		return credNo;
	}

	public void setCredNo(String credNo) {
		this.credNo = credNo;
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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
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

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	public String getBankAccNo() {
		return bankAccNo;
	}

	public void setBankAccNo(String bankAccNo) {
		this.bankAccNo = bankAccNo;
	}

	public BigDecimal getOverdraftSum() {
		return overdraftSum;
	}

	public void setOverdraftSum(BigDecimal overdraftSum) {
		this.overdraftSum = overdraftSum;
	}

	public BigDecimal getOverdraftBal() {
		return overdraftBal;
	}

	public void setOverdraftBal(BigDecimal overdraftBal) {
		this.overdraftBal = overdraftBal;
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

	public String getBillDate() {
		return billDate;
	}

	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}

	public long getPayDay() {
		return payDay;
	}

	public void setPayDay(long payDay) {
		this.payDay = payDay;
	}

	public String getCardBranch() {
		return cardBranch;
	}

	public void setCardBranch(String cardBranch) {
		this.cardBranch = cardBranch;
	}

	/**
	 * 获取“状态”名称
	 */
	public String getStatusName() {
		return CommonState.ALL.get(this.status) == null ? "" : CommonState
				.valueOf(this.status).getName();
	}

	/**
	 * 获取“证件类型”名称
	 */
	public String getCredTypeName() {
		return CertType.ALL.get(this.credType) == null ? "" : CertType.valueOf(
				this.credType).getName();
	}

	/**
	 * 获取“充值返利方式”名称
	 */
	public String getRebateTypeName() {
		return RebateType.ALL.get(this.rebateType) == null ? "" : RebateType
				.valueOf(this.rebateType).getName();
	}

}