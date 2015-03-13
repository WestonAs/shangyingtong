package gnete.card.entity;

import gnete.card.entity.state.CardCustomerState;
import gnete.card.entity.type.CertType;
import gnete.card.entity.type.CustomerRebateType;
import gnete.card.entity.type.RiskLevelType;
import gnete.etc.Symbol;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class CardCustomer {
	private Long cardCustomerId;

	private String cardCustomerName;

	private String groupCardId;

	private String rebateType;

	private String bank;

	private String bankNo;

	private String bankAccNo;

	private String contact;

	private String zip;

	private String address;

	private String phone;

	private String fax;

	private String email;

	private String status;

	private String updateUser;

	private Date updateTime;

	private String cardBranch;

	private String rebateCard;

	private String isCommon;
	
	/** 01 身份证 02护照 03 驾驶证 04回乡证(港澳台) 05 军官证 06 户口本 07企业组织机构代码  09 其他 10 学生证 */
	private String credType;
	
	/** 证件号码 */
	private String credNo;
	
	/** 营业执照 */
	private String license;
	
	/** 组织机构代码 */
	private String organization;

	/** 国/地税号 */
	private String taxNo;
	
	/** 录入机构号 */
	private String inputBranch;

	private String companyAddress;
	private String companyBusinessScope;
	private String credNoExpiryDate;
	private String career;
	private String nationality;
	private String riskLevel;
	

	/**
	 * 状态名
	 */
	public String getStatusName() {
		return CardCustomerState.ALL.get(this.status) == null ? ""
				: CardCustomerState.valueOf(this.status).getName();
	}

	/**
	 * 返利类型名
	 */
	public String getRebateTypeName() {
		return CustomerRebateType.ALL.get(rebateType) == null ? ""
				: CustomerRebateType.valueOf(rebateType).getName();
	}

	/**
	 * 是否通用名
	 */
	public String getIsCommonName() {
		if (StringUtils.isNotBlank(isCommon)) {
			if (Symbol.YES.equals(isCommon)) {
				return "是";
			} else {
				return "否";
			}
		} else {
			return "";
		}
	}
	/**
	 * 证件类型名
	 * @return
	 */
	public String getCredTypeName() {
		return CertType.ALL.get(this.credType) == null ? "" : CertType.valueOf(this.credType).getName();
	}
	
	public String getriskLevelDesc() {
		return RiskLevelType.ALL.get(this.riskLevel) == null ? "" : RiskLevelType.valueOf(this.riskLevel)
				.getName();
	}
	
	
	// ------------------------------- getter and setter followed------------------------
	
	public Long getCardCustomerId() {
		return cardCustomerId;
	}

	public void setCardCustomerId(Long cardCustomerId) {
		this.cardCustomerId = cardCustomerId;
	}

	public String getCardCustomerName() {
		return cardCustomerName;
	}

	public void setCardCustomerName(String cardCustomerName) {
		this.cardCustomerName = cardCustomerName;
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

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
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

	public String getCardBranch() {
		return cardBranch;
	}

	public void setCardBranch(String cardBranch) {
		this.cardBranch = cardBranch;
	}

	public String getRebateCard() {
		return rebateCard;
	}

	public void setRebateCard(String rebateCard) {
		this.rebateCard = rebateCard;
	}

	public String getIsCommon() {
		return isCommon;
	}

	public void setIsCommon(String isCommon) {
		this.isCommon = isCommon;
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

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getTaxNo() {
		return taxNo;
	}

	public void setTaxNo(String taxNo) {
		this.taxNo = taxNo;
	}

	public String getInputBranch() {
		return inputBranch;
	}

	public void setInputBranch(String inputBranch) {
		this.inputBranch = inputBranch;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public String getCompanyBusinessScope() {
		return companyBusinessScope;
	}

	public void setCompanyBusinessScope(String companyBusinessScope) {
		this.companyBusinessScope = companyBusinessScope;
	}

	public String getCredNoExpiryDate() {
		return credNoExpiryDate;
	}

	public void setCredNoExpiryDate(String credNoExpiryDate) {
		this.credNoExpiryDate = credNoExpiryDate;
	}

	public String getCareer() {
		return career;
	}

	public void setCareer(String career) {
		this.career = career;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getRiskLevel() {
		return riskLevel;
	}

	public void setRiskLevel(String riskLevel) {
		this.riskLevel = riskLevel;
	}
}