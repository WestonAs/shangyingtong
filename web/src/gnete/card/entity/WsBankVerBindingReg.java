package gnete.card.entity;

import gnete.card.entity.state.WsBankVerBindingRegSetStyle;
import gnete.card.entity.state.WsBankVerBindingRegState;
import gnete.card.entity.type.AcctType;

import java.util.Date;

public class WsBankVerBindingReg {

	private Long	bindingId;

	private String	cardId;

	private String	extCardId;

	private String	cardIssuer;

	private String	isDefault;

	private String	setStyle;

	private String	setType;

	private String	random;

	private String	userName;

	private String	idcard;

	private String	idType;

	private String	mobile;

	private String	bankAcctName;

	private String	bankCode;

	private String	bankAcct;

	private String	bankPwd;

	private String	verMode;

	private String	signValue;

	private String	reserved1;

	private String	remark;

	private Date	updateTime;

	private String	updateUser;

	private String	status;

	private String	rspCode;

	private String	transSn;

	private String	postScript;

	private String	cvn2;

	private String	bankNo;

	private String	bankCardType;

	private String	bankCardBin;

	private String	validDate;

	private String	verStatus;

	private String	posData;

	private String	verifyResult;

	private String	verifyRange;

	private String	deductResult;

	private String	deductRange;

	private String	accAreaCode;

	private String	bankName;

	private String	bankaccttype;

	/** 获得状态字段对应的名称 */
	public String getStatusName() {
		return WsBankVerBindingRegState.ALL.get(this.status) == null ? "" : WsBankVerBindingRegState.valueOf(
				this.status).getName();
	}

	/** 获得绑定方式对应的名称 */
	public String getSetStyleName() {
		return WsBankVerBindingRegSetStyle.ALL.get(this.setStyle) == null ? "" : WsBankVerBindingRegSetStyle
				.valueOf(this.setStyle).getName();
	}

	/** 获得银行账户类型（对私、对公）的名称 */
	public String getBankaccttypeName() {
		return AcctType.ALL.get(this.setStyle) == null ? "" : AcctType.valueOf(this.setStyle).getName();
	}

	public Long getBindingId() {
		return bindingId;
	}

	public void setBindingId(Long bindingId) {
		this.bindingId = bindingId;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getExtCardId() {
		return extCardId;
	}

	public void setExtCardId(String extCardId) {
		this.extCardId = extCardId;
	}

	public String getCardIssuer() {
		return cardIssuer;
	}

	public void setCardIssuer(String cardIssuer) {
		this.cardIssuer = cardIssuer;
	}

	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}

	public String getSetStyle() {
		return setStyle;
	}

	public void setSetStyle(String setStyle) {
		this.setStyle = setStyle;
	}

	public String getSetType() {
		return setType;
	}

	public void setSetType(String setType) {
		this.setType = setType;
	}

	public String getRandom() {
		return random;
	}

	public void setRandom(String random) {
		this.random = random;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getBankAcctName() {
		return bankAcctName;
	}

	public void setBankAcctName(String bankAcctName) {
		this.bankAcctName = bankAcctName;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankAcct() {
		return bankAcct;
	}

	public void setBankAcct(String bankAcct) {
		this.bankAcct = bankAcct;
	}

	public String getBankPwd() {
		return bankPwd;
	}

	public void setBankPwd(String bankPwd) {
		this.bankPwd = bankPwd;
	}

	public String getVerMode() {
		return verMode;
	}

	public void setVerMode(String verMode) {
		this.verMode = verMode;
	}

	public String getSignValue() {
		return signValue;
	}

	public void setSignValue(String signValue) {
		this.signValue = signValue;
	}

	public String getReserved1() {
		return reserved1;
	}

	public void setReserved1(String reserved1) {
		this.reserved1 = reserved1;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRspCode() {
		return rspCode;
	}

	public void setRspCode(String rspCode) {
		this.rspCode = rspCode;
	}

	public String getTransSn() {
		return transSn;
	}

	public void setTransSn(String transSn) {
		this.transSn = transSn;
	}

	public String getPostScript() {
		return postScript;
	}

	public void setPostScript(String postScript) {
		this.postScript = postScript;
	}

	public String getCvn2() {
		return cvn2;
	}

	public void setCvn2(String cvn2) {
		this.cvn2 = cvn2;
	}

	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	public String getBankCardType() {
		return bankCardType;
	}

	public void setBankCardType(String bankCardType) {
		this.bankCardType = bankCardType;
	}

	public String getBankCardBin() {
		return bankCardBin;
	}

	public void setBankCardBin(String bankCardBin) {
		this.bankCardBin = bankCardBin;
	}

	public String getValidDate() {
		return validDate;
	}

	public void setValidDate(String validDate) {
		this.validDate = validDate;
	}

	public String getVerStatus() {
		return verStatus;
	}

	public void setVerStatus(String verStatus) {
		this.verStatus = verStatus;
	}

	public String getPosData() {
		return posData;
	}

	public void setPosData(String posData) {
		this.posData = posData;
	}

	public String getVerifyResult() {
		return verifyResult;
	}

	public void setVerifyResult(String verifyResult) {
		this.verifyResult = verifyResult;
	}

	public String getVerifyRange() {
		return verifyRange;
	}

	public void setVerifyRange(String verifyRange) {
		this.verifyRange = verifyRange;
	}

	public String getDeductResult() {
		return deductResult;
	}

	public void setDeductResult(String deductResult) {
		this.deductResult = deductResult;
	}

	public String getDeductRange() {
		return deductRange;
	}

	public void setDeductRange(String deductRange) {
		this.deductRange = deductRange;
	}

	public String getAccAreaCode() {
		return accAreaCode;
	}

	public void setAccAreaCode(String accAreaCode) {
		this.accAreaCode = accAreaCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankaccttype() {
		return bankaccttype;
	}

	public void setBankaccttype(String bankaccttype) {
		this.bankaccttype = bankaccttype;
	}
}