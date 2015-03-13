package gnete.card.entity;

import gnete.card.entity.state.RegisterState;
import gnete.card.entity.type.CertType;
import gnete.card.entity.type.RenewType;

import java.util.Date;

public class RenewCardReg {

	private Long renewCardId;

	private String newCardId;

	private String cardId;

	private String acctId;

	private String custName;

	private String certType;

	private String certNo;

	private String renewType;

	private String status;

	private String updateUser;

	private Date updateTime;

	private String remark;

	private String branchCode;

	private String branchName;
	
	/** 发卡机构 */
	private String cardBranch;
	
	/** 领卡机构 */
	private String appOrgId;

	public String getNewCardId() {
		return newCardId;
	}

	public void setNewCardId(String newCardId) {
		this.newCardId = newCardId;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getAcctId() {
		return acctId;
	}

	public void setAcctId(String acctId) {
		this.acctId = acctId;
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

	public String getRenewType() {
		return renewType;
	}

	public void setRenewType(String renewType) {
		this.renewType = renewType;
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

	public String getCertTypeName() {
		return CertType.ALL.get(this.certType) == null ? "" : CertType.valueOf(this.certType).getName();
	}

	public String getRenewTypeName() {
		return RenewType.ALL.get(this.renewType) == null ? "" : RenewType.valueOf(this.renewType).getName();
	}

	public String getStatusName() {
		return RegisterState.ALL.get(this.status) == null ? "" : RegisterState.valueOf(this.status).getName();
	}

	/**
	 * @return the renewCardId
	 */
	public Long getRenewCardId() {
		return renewCardId;
	}

	/**
	 * @param renewCardId
	 *            the renewCardId to set
	 */
	public void setRenewCardId(Long renewCardId) {
		this.renewCardId = renewCardId;
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

	public String getCardBranch() {
		return cardBranch;
	}

	public void setCardBranch(String cardBranch) {
		this.cardBranch = cardBranch;
	}

	public String getAppOrgId() {
		return appOrgId;
	}

	public void setAppOrgId(String appOrgId) {
		this.appOrgId = appOrgId;
	}

}