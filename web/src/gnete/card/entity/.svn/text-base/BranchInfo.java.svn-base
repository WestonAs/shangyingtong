package gnete.card.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import flink.util.Cn2PinYinHelper;
import gnete.card.entity.flag.YesOrNoFlag;
import gnete.card.entity.state.BranchState;
import gnete.card.entity.type.AcctMediaType;
import gnete.card.entity.type.AcctType;
import gnete.card.entity.type.RiskLevelType;

/**
 * 
 * @Project: Card
 * @File: BranchInfo.java
 * @See:
 * @description： <li>增加拼音模糊查询</li>
 * @author:
 * @modified: aps-zbw
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2011-6-9
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2011 版权所有
 * @version: V1.0
 */
public class BranchInfo implements Serializable {

	private static final long	serialVersionUID	= 1L;

	private String				branchCode;

	private String				branchName;

	private String				branchAbbname;

	private String				areaCode;

	private String				parent;

	private String				bankNo;

	private String				bankName;

	private String				accNo;

	private String				accName;

	private String				contact;

	private String				address;

	private String				zip;

	private String				phone;

	private String				fax;

	private String				email;

	private String				status;

	private String				updateUser;

	private Date				updateTime;

	private String				developSide;

	private String				proxy;

	private String				isSettle;

	private String				curCode;

	private String				branchIndex;

	private String				accAreaCode;

	private BigDecimal			cardRiskAmt;

	private String				adminId;

	/** 账户类型 */
	private String				acctType;

	/** 上级机构 */
	private String				superBranchCode;

	// === 新增以下字段 ===========
	/** 营业执照 */
	private String				license;

	/** 组织机构号 */
	private String				organization;

	/** 备注 */
	private String				remark;

	/** 是否单机产品发卡机构 */
	private String				singleProduct;

	/** 账户介质类型 */
	private String				acctMediaType;

	/** 风险级别，0表示低，1表示中，2表示高 */
	private String				riskLevel;
	/** 营业执照有效期，到期日 */
	private Date				licenseExpDate;
	/** 法人身份证号码 */
	private String				legalPersonIdcard;
	/** 法人身份证有效期，到期日 */
	private Date				legalPersonIdcardExpDate;

	private String				legalPersonName;
	private String				taxRegCode;
	private String				organizationExpireDate;

	@Override
	public String toString() {
		return "BranchInfo ["
				+ (branchCode != null ? "branchCode=" + branchCode + ", " : "")
				+ (branchName != null ? "branchName=" + branchName + ", " : "")
				+ (branchAbbname != null ? "branchAbbname=" + branchAbbname + ", " : "")
				+ (areaCode != null ? "areaCode=" + areaCode + ", " : "")
				+ (parent != null ? "parent=" + parent + ", " : "")
				+ (bankNo != null ? "bankNo=" + bankNo + ", " : "")
				+ (bankName != null ? "bankName=" + bankName + ", " : "")
				+ (accNo != null ? "accNo=" + accNo + ", " : "")
				+ (accName != null ? "accName=" + accName + ", " : "")
				+ (contact != null ? "contact=" + contact + ", " : "")
				+ (address != null ? "address=" + address + ", " : "")
				+ (zip != null ? "zip=" + zip + ", " : "")
				+ (phone != null ? "phone=" + phone + ", " : "")
				+ (fax != null ? "fax=" + fax + ", " : "")
				+ (email != null ? "email=" + email + ", " : "")
				+ (status != null ? "status=" + status + ", " : "")
				+ (updateUser != null ? "updateUser=" + updateUser + ", " : "")
				+ (updateTime != null ? "updateTime=" + updateTime + ", " : "")
				+ (developSide != null ? "developSide=" + developSide + ", " : "")
				+ (proxy != null ? "proxy=" + proxy + ", " : "")
				+ (isSettle != null ? "isSettle=" + isSettle + ", " : "")
				+ (curCode != null ? "curCode=" + curCode + ", " : "")
				+ (branchIndex != null ? "branchIndex=" + branchIndex + ", " : "")
				+ (accAreaCode != null ? "accAreaCode=" + accAreaCode + ", " : "")
				+ (cardRiskAmt != null ? "cardRiskAmt=" + cardRiskAmt + ", " : "")
				+ (adminId != null ? "adminId=" + adminId + ", " : "")
				+ (acctType != null ? "acctType=" + acctType + ", " : "")
				+ (superBranchCode != null ? "superBranchCode=" + superBranchCode + ", " : "")
				+ (license != null ? "license=" + license + ", " : "")
				+ (organization != null ? "organization=" + organization + ", " : "")
				+ (remark != null ? "remark=" + remark + ", " : "")
				+ (singleProduct != null ? "singleProduct=" + singleProduct + ", " : "")
				+ (acctMediaType != null ? "acctMediaType=" + acctMediaType + ", " : "")
				+ (riskLevel != null ? "riskLevel=" + riskLevel + ", " : "")
				+ (licenseExpDate != null ? "licenseExpDate=" + licenseExpDate + ", " : "")
				+ (legalPersonIdcard != null ? "legalPersonIdcard=" + legalPersonIdcard + ", " : "")
				+ (legalPersonIdcardExpDate != null ? "legalPersonIdcardExpDate=" + legalPersonIdcardExpDate
						+ ", " : "")
				+ (legalPersonName != null ? "legalPersonName=" + legalPersonName + ", " : "")
				+ (taxRegCode != null ? "taxRegCode=" + taxRegCode + ", " : "")
				+ (organizationExpireDate != null ? "organizationExpireDate=" + organizationExpireDate : "")
				+ "]";
	}

	/**
	 * 
	 * @description：JSON中的属性为[branchPinYin]
	 * @return
	 * @version: 2011-6-9 下午01:46:29
	 * @See:
	 */
	public String getBranchPinYin() {
		return Cn2PinYinHelper.cn2Spell(this.branchName);
	}

	/**
	 * 状态名
	 * 
	 * @return
	 */
	public String getStatusName() {
		return StringUtils.isEmpty(this.status) ? "" : BranchState.valueOf(this.status).getName();
	}

	/**
	 * 是否是 待审核状态
	 * 
	 * @return
	 */
	public boolean isWaitedStatus() {
		return BranchState.WAITED.getValue().equals(this.getStatus());
	}

	/**
	 * 是否是 审核不通过状态
	 * 
	 * @return
	 */
	public boolean isUnpassStatus() {
		return BranchState.UNPASS.getValue().equals(this.getStatus());
	}

	/**
	 * 账户类型名
	 * 
	 * @return
	 */
	public String getAcctTypeName() {
		return AcctType.ALL.get(this.acctType) == null ? "" : AcctType.valueOf(this.acctType).getName();
	}

	/**
	 * 是否单机产品名
	 * 
	 * @return
	 */
	public String getSingleProductName() {
		return YesOrNoFlag.ALL.get(this.singleProduct) == null ? "" : YesOrNoFlag.valueOf(this.singleProduct)
				.getName();
	}

	/**
	 * 账户介质类型名
	 * 
	 * @return
	 */
	public String getAcctMediaTypeName() {
		return AcctMediaType.ALL.get(acctMediaType) == null ? "" : AcctMediaType.valueOf(acctMediaType)
				.getName();
	}

	/**
	 * 获得风险级别描述：低、中、高
	 * 
	 * @return
	 */
	public String getRiskLevelName() {
		return RiskLevelType.ALL.get(this.getRiskLevel()) == null ? "" : RiskLevelType.valueOf(
				this.getRiskLevel()).getName();
	}

	// ------------------------------- getter and setter followed------------------------

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

	public String getBranchAbbname() {
		return branchAbbname;
	}

	public void setBranchAbbname(String branchAbbname) {
		this.branchAbbname = branchAbbname;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getAccNo() {
		return accNo;
	}

	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}

	public String getAccName() {
		return accName;
	}

	public void setAccName(String accName) {
		this.accName = accName;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
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

	public String getDevelopSide() {
		return developSide;
	}

	public void setDevelopSide(String developSide) {
		this.developSide = developSide;
	}

	public String getProxy() {
		return proxy;
	}

	public void setProxy(String proxy) {
		this.proxy = proxy;
	}

	public String getCurCode() {
		return curCode;
	}

	public void setCurCode(String curCode) {
		this.curCode = curCode;
	}

	public String getBranchIndex() {
		return branchIndex;
	}

	public void setBranchIndex(String branchIndex) {
		this.branchIndex = branchIndex;
	}

	public String getIsSettle() {
		return isSettle;
	}

	public void setIsSettle(String isSettle) {
		this.isSettle = isSettle;
	}

	public String getAccAreaCode() {
		return accAreaCode;
	}

	public void setAccAreaCode(String accAreaCode) {
		this.accAreaCode = accAreaCode;
	}

	public BigDecimal getCardRiskAmt() {
		return cardRiskAmt;
	}

	public void setCardRiskAmt(BigDecimal cardRiskAmt) {
		this.cardRiskAmt = cardRiskAmt;
	}

	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	public String getAcctType() {
		return acctType;
	}

	public void setAcctType(String acctType) {
		this.acctType = acctType;
	}

	public String getSuperBranchCode() {
		return superBranchCode;
	}

	public void setSuperBranchCode(String superBranchCode) {
		this.superBranchCode = superBranchCode;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSingleProduct() {
		return singleProduct;
	}

	public void setSingleProduct(String singleProduct) {
		this.singleProduct = singleProduct;
	}

	public String getAcctMediaType() {
		return acctMediaType;
	}

	public void setAcctMediaType(String acctMediaType) {
		this.acctMediaType = acctMediaType;
	}

	public String getRiskLevel() {
		return riskLevel;
	}

	public void setRiskLevel(String riskLevel) {
		this.riskLevel = riskLevel;
	}

	public Date getLicenseExpDate() {
		return licenseExpDate;
	}

	public void setLicenseExpDate(Date licenseExpDate) {
		this.licenseExpDate = licenseExpDate;
	}

	public String getLegalPersonIdcard() {
		return legalPersonIdcard;
	}

	public void setLegalPersonIdcard(String legalPersonIdcard) {
		this.legalPersonIdcard = legalPersonIdcard;
	}

	public Date getLegalPersonIdcardExpDate() {
		return legalPersonIdcardExpDate;
	}

	public void setLegalPersonIdcardExpDate(Date legalPersonIdcardExpDate) {
		this.legalPersonIdcardExpDate = legalPersonIdcardExpDate;
	}

	public String getLegalPersonName() {
		return legalPersonName;
	}

	public void setLegalPersonName(String legalPersonName) {
		this.legalPersonName = legalPersonName;
	}

	public String getTaxRegCode() {
		return taxRegCode;
	}

	public void setTaxRegCode(String taxRegCode) {
		this.taxRegCode = taxRegCode;
	}

	public String getOrganizationExpireDate() {
		return organizationExpireDate;
	}

	public void setOrganizationExpireDate(String organizationExpireDate) {
		this.organizationExpireDate = organizationExpireDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}