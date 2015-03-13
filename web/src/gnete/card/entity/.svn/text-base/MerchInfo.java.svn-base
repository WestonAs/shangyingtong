package gnete.card.entity;

import flink.util.Cn2PinYinHelper;
import gnete.card.entity.flag.OpenFlag;
import gnete.card.entity.flag.SetCycleFlag;
import gnete.card.entity.flag.TrueOrFalseFlag;
import gnete.card.entity.flag.UsePwdFlag;
import gnete.card.entity.flag.YesOrNoFlag;
import gnete.card.entity.state.MerchState;
import gnete.card.entity.type.AcctMediaType;
import gnete.card.entity.type.AcctType;
import gnete.card.entity.type.RiskLevelType;

import java.util.Date;

public class MerchInfo {
	private String	merchId;

	private String	merchName;

	private String	merchAbb;

	private String	merchEn;

	/**营业执照编号*/
	private String	merchCode;

	private String	merchType;

	private String	merchAddress;

	private String	linkMan;

	private String	telNo;

	private String	faxNo;

	private String	areaCode;

	private String	email;

	private String	unPay;

	private String	bankNo;

	private String	bankName;

	private String	accNo;

	private String	accName;

	private String	setCycle;

	private Date	businessTime;

	private Date	createTime;

	private Date	signingTime;

	private String	openFlag;

	private String	currCode;

	private String	merchLevel;

	private String	parent;

	private String	branchCode;

	private String	status;

	private String	updateBy;

	private Date	updateTime;

	private String	manageBranch;

	private String	usePwdFlag;

	private String	isNetting;

	private String	accAreaCode;

	// 表中新加字段
	private String	adminId;

	/** 直属发卡机构号，商户与该机构是同一法人（当从添加发卡机构页面跳转过来时，此字段不为空） */
	private String	cardBranch;

	private String	acctType;

	/** 是否是单机产品商户 */
	private String	singleProduct;

	/** 账户介质类型 */
	private String	acctMediaType;

	/** 风险级别，0表示低，1表示中，2表示高 */
	private String	riskLevel;
	/** 营业执照有效期，到期日 */
	private Date	licenseExpDate;
	/** 法人身份证号码 */
	private String	legalPersonIdcard;
	/** 法人身份证有效期，到期日 */
	private Date	legalPersonIdcardExpDate;
	/** 易航宝虚拟帐号 */
	private String	yhbVirtualAcct;
	/** 备注 */
	private String	remark;

	private String	companyBusinessScope;

	private String	legalPersonName;
	private String	taxRegCode;
	private String	organization;
	private String	organizationExpireDate;

	public String getStatusName() {
		return MerchState.ALL.get(this.status) == null ? "" : MerchState.valueOf(this.status).getName();
	}

	public String getUsePwdFlagName() {
		return UsePwdFlag.ALL.get(this.usePwdFlag) == null ? "" : UsePwdFlag.valueOf(this.usePwdFlag)
				.getName();
	}

	public String getOpenFlagName() {
		return OpenFlag.ALL.get(this.openFlag) == null ? "" : OpenFlag.valueOf(this.openFlag).getName();
	}

	public String getSetCycleName() {
		return SetCycleFlag.ALL.get(this.setCycle) == null ? "" : SetCycleFlag.valueOf(this.setCycle)
				.getName();
	}

	/**
	 * 
	 * @description：JSON中的属性为[merchPinYin]
	 * @return
	 * @version: 2011-6-9 下午01:53:02
	 * @See:
	 */
	public String getMerchPinYin() {
		return Cn2PinYinHelper.cn2Spell(this.merchName);
	}

	public String getIsNettingName() {
		return TrueOrFalseFlag.ALL.get(isNetting) == null ? "" : TrueOrFalseFlag.valueOf(isNetting).getName();
	}

	public String getAcctTypeName() {
		return AcctType.ALL.get(this.acctType) == null ? "" : AcctType.valueOf(this.acctType).getName();
	}

	/**
	 * 是否单机产品商户名
	 * 
	 * @return
	 */
	public String getSingleProductName() {
		return YesOrNoFlag.ALL.get(singleProduct) == null ? "" : YesOrNoFlag.valueOf(singleProduct).getName();
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

	public String getMerchId() {
		return merchId;
	}

	public void setMerchId(String merchId) {
		this.merchId = merchId;
	}

	public String getMerchName() {
		return merchName;
	}

	public void setMerchName(String merchName) {
		this.merchName = merchName;
	}

	public String getMerchAbb() {
		return merchAbb;
	}

	public void setMerchAbb(String merchAbb) {
		this.merchAbb = merchAbb;
	}

	public String getMerchEn() {
		return merchEn;
	}

	public void setMerchEn(String merchEn) {
		this.merchEn = merchEn;
	}

	public String getMerchCode() {
		return merchCode;
	}

	public void setMerchCode(String merchCode) {
		this.merchCode = merchCode;
	}

	public String getMerchType() {
		return merchType;
	}

	public void setMerchType(String merchType) {
		this.merchType = merchType;
	}

	public String getMerchAddress() {
		return merchAddress;
	}

	public void setMerchAddress(String merchAddress) {
		this.merchAddress = merchAddress;
	}

	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	public String getTelNo() {
		return telNo;
	}

	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}

	public String getFaxNo() {
		return faxNo;
	}

	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUnPay() {
		return unPay;
	}

	public void setUnPay(String unPay) {
		this.unPay = unPay;
	}

	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
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

	public String getSetCycle() {
		return setCycle;
	}

	public void setSetCycle(String setCycle) {
		this.setCycle = setCycle;
	}

	public Date getBusinessTime() {
		return businessTime;
	}

	public void setBusinessTime(Date businessTime) {
		this.businessTime = businessTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getSigningTime() {
		return signingTime;
	}

	public void setSigningTime(Date signingTime) {
		this.signingTime = signingTime;
	}

	public String getOpenFlag() {
		return openFlag;
	}

	public void setOpenFlag(String openFlag) {
		this.openFlag = openFlag;
	}

	public String getCurrCode() {
		return currCode;
	}

	public void setCurrCode(String currCode) {
		this.currCode = currCode;
	}

	public String getMerchLevel() {
		return merchLevel;
	}

	public void setMerchLevel(String merchLevel) {
		this.merchLevel = merchLevel;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getManageBranch() {
		return manageBranch;
	}

	public void setManageBranch(String manageBranch) {
		this.manageBranch = manageBranch;
	}

	public String getUsePwdFlag() {
		return usePwdFlag;
	}

	public void setUsePwdFlag(String usePwdFlag) {
		this.usePwdFlag = usePwdFlag;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getAccAreaCode() {
		return accAreaCode;
	}

	public void setAccAreaCode(String accAreaCode) {
		this.accAreaCode = accAreaCode;
	}

	public String getIsNetting() {
		return isNetting;
	}

	public void setIsNetting(String isNetting) {
		this.isNetting = isNetting;
	}

	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	public String getCardBranch() {
		return cardBranch;
	}

	public void setCardBranch(String cardBranch) {
		this.cardBranch = cardBranch;
	}

	public String getAcctType() {
		return acctType;
	}

	public void setAcctType(String acctType) {
		this.acctType = acctType;
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

	public String getYhbVirtualAcct() {
		return yhbVirtualAcct;
	}

	public void setYhbVirtualAcct(String yhbVirtualAcct) {
		this.yhbVirtualAcct = yhbVirtualAcct;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCompanyBusinessScope() {
		return companyBusinessScope;
	}

	public void setCompanyBusinessScope(String companyBusinessScope) {
		this.companyBusinessScope = companyBusinessScope;
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

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getOrganizationExpireDate() {
		return organizationExpireDate;
	}

	public void setOrganizationExpireDate(String organizationExpireDate) {
		this.organizationExpireDate = organizationExpireDate;
	}

}