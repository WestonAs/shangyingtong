package gnete.card.entity;

import gnete.card.entity.flag.MakeFlag;
import gnete.card.entity.state.MakeCardAppState;

import java.math.BigDecimal;
import java.util.Date;

public class MakeCardApp {
	private Long Id;
	
	private String appId;

	private String makeId;
	
	private String cardSubtype;
	
	private String binNo;
	
	private String branchCode;

	private String merchId;

	private BigDecimal amount;

	private String strNo;

	private BigDecimal cardNum;

	private String makeFlag;

	private BigDecimal wasteNum;

	private String deliveryUnit;

	private String deliveryUser;

	private String deliveryAdd;

	private String recvUser;

	private String recvContact;

	private String appDate;

	private String status;

	private String makeInfo;

	private String memo;

	private String appUser;

	private String cancelUser;

	private String cancelDate;

	private String chkUser;

	private String chkDate;

	private String reason;

	private String updateBy;

	private Date updateTime;

	//---------- 非数据库表字段 ----------------
	
	/** 制卡等级名 */
	private String makeName;
	/** 发卡机构名称 */
	private String branchName;
	/** 关联的卡子类型的参考面值 */
	private BigDecimal faceValue;

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getMakeId() {
		return makeId;
	}

	public void setMakeId(String makeId) {
		this.makeId = makeId;
	}

	public String getCardSubtype() {
		return cardSubtype;
	}

	public void setCardSubtype(String cardSubtype) {
		this.cardSubtype = cardSubtype;
	}

	public String getBinNo() {
		return binNo;
	}

	public void setBinNo(String binNo) {
		this.binNo = binNo;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getMerchId() {
		return merchId;
	}

	public void setMerchId(String merchId) {
		this.merchId = merchId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getStrNo() {
		return strNo;
	}

	public void setStrNo(String strNo) {
		this.strNo = strNo;
	}

	public BigDecimal getCardNum() {
		return cardNum;
	}

	public void setCardNum(BigDecimal cardNum) {
		this.cardNum = cardNum;
	}

	public String getMakeFlag() {
		return makeFlag;
	}

	public void setMakeFlag(String makeFlag) {
		this.makeFlag = makeFlag;
	}

	public BigDecimal getWasteNum() {
		return wasteNum;
	}

	public void setWasteNum(BigDecimal wasteNum) {
		this.wasteNum = wasteNum;
	}

	public String getDeliveryUnit() {
		return deliveryUnit;
	}

	public void setDeliveryUnit(String deliveryUnit) {
		this.deliveryUnit = deliveryUnit;
	}

	public String getDeliveryUser() {
		return deliveryUser;
	}

	public void setDeliveryUser(String deliveryUser) {
		this.deliveryUser = deliveryUser;
	}

	public String getDeliveryAdd() {
		return deliveryAdd;
	}

	public void setDeliveryAdd(String deliveryAdd) {
		this.deliveryAdd = deliveryAdd;
	}

	public String getRecvUser() {
		return recvUser;
	}

	public void setRecvUser(String recvUser) {
		this.recvUser = recvUser;
	}

	public String getRecvContact() {
		return recvContact;
	}

	public void setRecvContact(String recvContact) {
		this.recvContact = recvContact;
	}

	public String getAppDate() {
		return appDate;
	}

	public void setAppDate(String appDate) {
		this.appDate = appDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMakeInfo() {
		return makeInfo;
	}

	public void setMakeInfo(String makeInfo) {
		this.makeInfo = makeInfo;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getAppUser() {
		return appUser;
	}

	public void setAppUser(String appUser) {
		this.appUser = appUser;
	}

	public String getCancelUser() {
		return cancelUser;
	}

	public void setCancelUser(String cancelUser) {
		this.cancelUser = cancelUser;
	}

	public String getCancelDate() {
		return cancelDate;
	}

	public void setCancelDate(String cancelDate) {
		this.cancelDate = cancelDate;
	}

	public String getChkUser() {
		return chkUser;
	}

	public void setChkUser(String chkUser) {
		this.chkUser = chkUser;
	}

	public String getChkDate() {
		return chkDate;
	}

	public void setChkDate(String chkDate) {
		this.chkDate = chkDate;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
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

	public String getMakeName() {
		return makeName;
	}

	public void setMakeName(String makeName) {
		this.makeName = makeName;
	}

	public String getStatusName() {
		return MakeCardAppState.ALL.get(this.status) == null ? ""
				: MakeCardAppState.valueOf(this.status).getName();
	}

	public String getMakeFlagName() {
		return MakeFlag.ALL.get(this.makeFlag) == null ? "" : MakeFlag.valueOf(
				this.makeFlag).getName();
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public BigDecimal getFaceValue() {
		return faceValue;
	}

	public void setFaceValue(BigDecimal faceValue) {
		this.faceValue = faceValue;
	}
	
}