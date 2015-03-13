package gnete.card.entity;

import gnete.card.entity.flag.OKFlag;
import gnete.card.entity.state.MakeCardRegState;
import gnete.card.entity.type.MakeCardType;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 制卡登记（卡样定版）
 * @author KANG
 *
 */
public class MakeCardReg {
	private String makeId;

	private String branchCode;

	private String cardSubtype;

	private String makeName;

	private String makeType;

	private String makeUser;

	private String initUrl;

	private String finUrl;

	private Date appUpload;

	private Date makeUpload;

	private String okFlag;

	private String reason;

	private String okDate;

	private String picStatus;

	private String smsFlag;

	private String updateBy;

	private Date updateTime;


	private String cardTypeName;
	
	private BigDecimal faceValue;

	public String getPicStatusName() {
		return MakeCardRegState.ALL.get(this.picStatus) == null ? ""
				: MakeCardRegState.valueOf(this.picStatus).getName();
	}

	public String getOkFlagName() {
		return OKFlag.ALL.get(this.okFlag) == null ? "" : OKFlag.valueOf(
				this.okFlag).getName();
	}

	public String getMakeTypeName() {
		return MakeCardType.ALL.get(this.makeType) == null ? "" : MakeCardType
				.valueOf(this.makeType).getName();
	}
	
	// ------------------------------- getter and setter followed------------------------
	
	public String getMakeId() {
		return makeId;
	}

	public void setMakeId(String makeId) {
		this.makeId = makeId;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getCardSubtype() {
		return cardSubtype;
	}

	public void setCardSubtype(String cardSubtype) {
		this.cardSubtype = cardSubtype;
	}

	public String getMakeName() {
		return makeName;
	}

	public void setMakeName(String makeName) {
		this.makeName = makeName;
	}

	public String getMakeType() {
		return makeType;
	}

	public void setMakeType(String makeType) {
		this.makeType = makeType;
	}

	public String getMakeUser() {
		return makeUser;
	}

	public void setMakeUser(String makeUser) {
		this.makeUser = makeUser;
	}

	public String getInitUrl() {
		return initUrl;
	}

	public void setInitUrl(String initUrl) {
		this.initUrl = initUrl;
	}

	public String getFinUrl() {
		return finUrl;
	}

	public void setFinUrl(String finUrl) {
		this.finUrl = finUrl;
	}

	public Date getAppUpload() {
		return appUpload;
	}

	public void setAppUpload(Date appUpload) {
		this.appUpload = appUpload;
	}

	public Date getMakeUpload() {
		return makeUpload;
	}

	public void setMakeUpload(Date makeUpload) {
		this.makeUpload = makeUpload;
	}

	public String getOkFlag() {
		return okFlag;
	}

	public void setOkFlag(String okFlag) {
		this.okFlag = okFlag;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getOkDate() {
		return okDate;
	}

	public void setOkDate(String okDate) {
		this.okDate = okDate;
	}

	public String getPicStatus() {
		return picStatus;
	}

	public void setPicStatus(String picStatus) {
		this.picStatus = picStatus;
	}

	public String getSmsFlag() {
		return smsFlag;
	}

	public void setSmsFlag(String smsFlag) {
		this.smsFlag = smsFlag;
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

	public String getCardTypeName() {
		return cardTypeName;
	}

	public void setCardTypeName(String cardTypeName) {
		this.cardTypeName = cardTypeName;
	}

	public BigDecimal getFaceValue() {
		return faceValue;
	}

	public void setFaceValue(BigDecimal faceValue) {
		this.faceValue = faceValue;
	}

}