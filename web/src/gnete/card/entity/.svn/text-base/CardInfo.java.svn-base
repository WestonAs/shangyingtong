package gnete.card.entity;

import gnete.card.entity.flag.TrueOrFalseFlag;
import gnete.card.entity.state.CardState;
import gnete.card.entity.type.CardType;
import gnete.card.entity.type.CertType;
import gnete.card.entity.type.PwdChangeStatusType;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.math.NumberUtils;

public class CardInfo {

	private String cardId;

	private String cardClass;

	private String cardSubclass;

	private String acctId;

	private String cardBin;

	private BigDecimal amount;

	private String effDate;

	private String expirDate;

	private String pwdflag;

	private String password;

	private String appOrgId;

	private String saleOrgId;

	private Long cardCustomerId;

	private String batchNo;

	private String createDate;

	private String cancelDate;

	private String appDate;

	private String saleDate;

	private String cardStatus;

	private Short extenLeft;

	private String pwdChangeStatus;

	private Short pwdMismatchCnt;

	private String cvn;

	private String cvn2;

	private String trackInfo;

	private String updateBy;

	private Date updateTime;
	
	private String membLevel;

	private String membClass;
	
	private String cardIssuer;
	
	private BigDecimal expenses;
	
	private String externalCardId;

	private BigDecimal consumedStoredValue;
	
	// 增加非字段变量 证件类型
	private String credType;
	private String custName;
	private String credNo;
	private String telNo;
	private String mobileNo;
	
	private String branchName;
	
	private String cardSubclassName;
	private BigDecimal accuChargeAmt;
	/** 双汇查询时，作为卡的返利资金余额使用*/
	private BigDecimal bal;
	
	/** 卡的初始金额 */
	private BigDecimal initAmt;
	/** 面值 */
	private BigDecimal faceValue;
	/** 消费总金额 */
	private BigDecimal consumeAmt;
	/** 卡的充值资金余额 */
	private BigDecimal balanceAmt;
	/** 消费总次数 */
	private BigDecimal consumeTimes;

	
	/** 
	 * 是否 预售出  状态
	 */
	public boolean isPreselledStatus(){
		return CardState.PRESELLED.getValue().equals(getCardStatus());
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

	public String getCardClassName() {
		return CardType.ALL.get(this.cardClass) == null ? "" : CardType.valueOf(this.cardClass).getName();
	}
	
	public void setCardClass(String cardClass) {
		this.cardClass = cardClass;
	}

	public String getCardSubclass() {
		return cardSubclass;
	}

	public void setCardSubclass(String cardSubclass) {
		this.cardSubclass = cardSubclass;
	}

	public String getAcctId() {
		return acctId;
	}

	public void setAcctId(String acctId) {
		this.acctId = acctId;
	}

	public String getCardBin() {
		return cardBin;
	}

	public void setCardBin(String cardBin) {
		this.cardBin = cardBin;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getEffDate() {
		return effDate;
	}

	public void setEffDate(String effDate) {
		this.effDate = effDate;
	}

	public String getExpirDate() {
		return expirDate;
	}

	public void setExpirDate(String expirDate) {
		this.expirDate = expirDate;
	}

	public String getPwdflag() {
		return pwdflag;
	}

	public void setPwdflag(String pwdflag) {
		this.pwdflag = pwdflag;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAppOrgId() {
		return appOrgId;
	}

	public void setAppOrgId(String appOrgId) {
		this.appOrgId = appOrgId;
	}

	public String getSaleOrgId() {
		return saleOrgId;
	}

	public void setSaleOrgId(String saleOrgId) {
		this.saleOrgId = saleOrgId;
	}

	public Long getCardCustomerId() {
		return cardCustomerId;
	}

	public void setCardCustomerId(Long cardCustomerId) {
		this.cardCustomerId = cardCustomerId;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getCancelDate() {
		return cancelDate;
	}

	public void setCancelDate(String cancelDate) {
		this.cancelDate = cancelDate;
	}

	public String getAppDate() {
		return appDate;
	}

	public void setAppDate(String appDate) {
		this.appDate = appDate;
	}

	public String getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(String saleDate) {
		this.saleDate = saleDate;
	}

	public String getCardStatus() {
		return cardStatus;
	}

	public void setCardStatus(String cardStatus) {
		this.cardStatus = cardStatus;
	}

	public Short getExtenLeft() {
		return extenLeft;
	}

	public void setExtenLeft(Short extenLeft) {
		this.extenLeft = extenLeft;
	}

	public String getPwdChangeStatus() {
		return pwdChangeStatus;
	}

	public void setPwdChangeStatus(String pwdChangeStatus) {
		this.pwdChangeStatus = pwdChangeStatus;
	}

	public Short getPwdMismatchCnt() {
		return pwdMismatchCnt;
	}

	public void setPwdMismatchCnt(Short pwdMismatchCnt) {
		this.pwdMismatchCnt = pwdMismatchCnt;
	}

	public String getCvn() {
		return cvn;
	}

	public void setCvn(String cvn) {
		this.cvn = cvn;
	}

	public String getCvn2() {
		return cvn2;
	}

	public void setCvn2(String cvn2) {
		this.cvn2 = cvn2;
	}

	public String getTrackInfo() {
		return trackInfo;
	}

	public void setTrackInfo(String trackInfo) {
		this.trackInfo = trackInfo;
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

	public String getMembLevel() {
		return membLevel;
	}

	public void setMembLevel(String membLevel) {
		this.membLevel = membLevel;
	}

	public String getMembClass() {
		return membClass;
	}

	public void setMembClass(String membClass) {
		this.membClass = membClass;
	}

	public String getCardStatusName() {
		return CardState.ALL.get(this.cardStatus) == null ? "" : CardState.valueOf(this.cardStatus).getName();
	}
	
	public String getPwdChangeStatusName() {
		return PwdChangeStatusType.ALL.get(this.pwdChangeStatus) == null ? "" : PwdChangeStatusType.valueOf(this.pwdChangeStatus).getName();
	}
	
	// 获得"是否使用密码"标志
    public String getPwdflagName(){
		return TrueOrFalseFlag.ALL.get(this.pwdflag) == null ? "" : TrueOrFalseFlag.valueOf(this.pwdflag).getName();
	}

	
	/**
	 * 根据卡号获得卡BIN
	 */
	public String getBinNo() {
		return CardBin.getBinNo(this.cardId);
	}

	/**
	 * 根据卡号取得卡号的序号
	 * @author aps-zwi
	 */
	public Long getCardNoSeq() {
		return cardId == null ? 0L : NumberUtils.toLong(cardId.substring(11, cardId.length() - 1));
	}

	public String getCardIssuer() {
		return cardIssuer;
	}

	public void setCardIssuer(String cardIssuer) {
		this.cardIssuer = cardIssuer;
	}

	public BigDecimal getExpenses() {
		return expenses;
	}

	public void setExpenses(BigDecimal expenses) {
		this.expenses = expenses;
	}

	/**
	 * 取得卡号的前11位
	 * @author aps-zwi
	 */
	public String getCardNoPrix() {
		return cardId == null ? "" : cardId.substring(0, 11);
	}

	/**
	 * @return the credType
	 */
	public String getCredType() {
		return credType;
	}

	/**
	 * @param credType the credType to set
	 */
	public void setCredType(String credType) {
		this.credType = credType;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getCredNo() {
		return credNo;
	}

	public void setCredNo(String credNo) {
		this.credNo = credNo;
	}

	public String getTelNo() {
		return telNo;
	}

	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getExternalCardId() {
		return externalCardId;
	}

	public void setExternalCardId(String externalCardId) {
		this.externalCardId = externalCardId;
	}

	public String getCardSubclassName() {
		return cardSubclassName;
	}

	public void setCardSubclassName(String cardSubclassName) {
		this.cardSubclassName = cardSubclassName;
	}

	public BigDecimal getAccuChargeAmt() {
		return accuChargeAmt;
	}

	public void setAccuChargeAmt(BigDecimal accuChargeAmt) {
		this.accuChargeAmt = accuChargeAmt;
	}

	public BigDecimal getBal() {
		return bal;
	}

	public void setBal(BigDecimal bal) {
		this.bal = bal;
	}
	
	public BigDecimal getFaceValue() {
		return faceValue;
	}

	public void setFaceValue(BigDecimal faceValue) {
		this.faceValue = faceValue;
	}

	public BigDecimal getConsumedStoredValue() {
		return consumedStoredValue;
	}

	public void setConsumedStoredValue(BigDecimal consumedStoredValue) {
		this.consumedStoredValue = consumedStoredValue;
	}

	public String getCredTypeName(){
		return CertType.ALL.get(this.credType) == null ? "" : CertType.valueOf(this.credType).getName();
	}

	public BigDecimal getConsumeAmt() {
		return consumeAmt;
	}

	public void setConsumeAmt(BigDecimal consumeAmt) {
		this.consumeAmt = consumeAmt;
	}

	public BigDecimal getBalanceAmt() {
		return balanceAmt;
	}

	public void setBalanceAmt(BigDecimal balanceAmt) {
		this.balanceAmt = balanceAmt;
	}

	public BigDecimal getConsumeTimes() {
		return consumeTimes;
	}

	public void setConsumeTimes(BigDecimal consumeTimes) {
		this.consumeTimes = consumeTimes;
	}

	public BigDecimal getInitAmt() {
		return initAmt;
	}

	public void setInitAmt(BigDecimal initAmt) {
		this.initAmt = initAmt;
	}
}