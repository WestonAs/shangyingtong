package gnete.card.entity;

import gnete.card.entity.flag.CardFlag;
import gnete.card.entity.flag.YesOrNoFlag;
import gnete.card.entity.flag.ecouponTypeFlag;
import gnete.card.entity.state.CheckState;
import gnete.card.entity.type.CardSubClassExpirMthd;
import gnete.card.entity.type.PasswordType;

import java.math.BigDecimal;
import java.util.Date;

public class CardSubClassDef {
	private String cardSubclass;

	private String cardSubclassName;

	/** 卡种 */
	private String cardClass;

	private String binNo;

	private String cardIssuer;

	private BigDecimal faceValue;

	private String membLevel;

	private BigDecimal ptOpencard;

	private String membClass;

	private String discntClass;

	private String frequencyClass;

	private String ptClass;

	private String couponClass;

	private BigDecimal cardPrice;

	private BigDecimal buyPriceMax;

	private BigDecimal buyPriceMin;

	private BigDecimal chkPwd;

	private String pwdType;

	private String pwd;

	private BigDecimal extenUlimit;

	private BigDecimal extenLlimit;

	private BigDecimal chkPfcard;

	private BigDecimal autoCancelFlag;

	private BigDecimal creditUlimit;

	private BigDecimal chargeUlimit;

	private BigDecimal consmUlimit;

	private Short effPeriod;

	private Short extenPeriod;

	private Short extenMaxcnt;

	private String mustExpirDate;

	private String cardTypeName;

	private String expirMthd;

	private String expirDate;

	// 数据库中新加字段
	private String status;

	private String updateBy;

	private Date updateTime;
	
	private String icType;
	
	private String icModelNo;
	
	/** 售卡面值是否可以修改 */
	private String changeFaceValue;
	
	/** 是否能充值 */
	private String depositFlag;
	
	/** 首次交易前是否强制修改密码 */
	private String isChgPwd;
	
	/** 电子券券种：'1'一次性消费券 ； '0'多次消费券*/
	private String ecouponType;


	/**
	 * 失效方式名
	 * 
	 * @return
	 */
	public String getExpirMthdName() {
		return CardSubClassExpirMthd.ALL.get(this.expirMthd) == null ? ""
				: CardSubClassExpirMthd.valueOf(this.expirMthd).getName();
	}

	public boolean isSpecifyDateExpire(){
		return CardSubClassExpirMthd.SPECIFY_DATE.getValue().equals(this.expirMthd);
	}
	
	/**
	 * 状态名
	 * 
	 * @return
	 */
	public String getStatusName() {
		return CheckState.ALL.get(this.status) == null ? "" : CheckState
				.valueOf(this.status).getName();
	}

	/** 密码类型名 */
	public String getPwdTypeName() {
		return PasswordType.ALL.get(pwdType) == null ? "" : PasswordType
				.valueOf(pwdType).getName();
	}
	
	/** 卡片类型名  */
	public String getIcTypeName() {
		return CardFlag.ALL.get(this.icType) == null ? "" : CardFlag.valueOf(this.icType).getName();
	}
	
	public String getChangeFaceValueName() {
		return YesOrNoFlag.ALL.get(changeFaceValue) == null ? "" : YesOrNoFlag.valueOf(changeFaceValue).getName();
	}
	
	public String getDepositFlagName() {
		return YesOrNoFlag.ALL.get(depositFlag) == null ? "" : YesOrNoFlag.valueOf(depositFlag).getName();
	}
	public String getEcouponTypeName(){
		return ecouponTypeFlag.ALL.get(ecouponType)==null ? "": ecouponTypeFlag.valueOf(ecouponType).getName();
	}
	
	/** 是否 纯IC卡或复合卡或M1卡 */
	public boolean isIcOrCombineOrM1Type(){
		return CardFlag.IC.getValue().equals(getIcType()) || CardFlag.COMBINE.getValue().equals(getIcType())
				|| CardFlag.M1.getValue().equals(getIcType());
	}
	// ------------------------------------- getter and setter followed---------------------
	
	public String getCardSubclass() {
		return cardSubclass;
	}

	public void setCardSubclass(String cardSubclass) {
		this.cardSubclass = cardSubclass;
	}

	public String getCardSubclassName() {
		return cardSubclassName;
	}

	public void setCardSubclassName(String cardSubclassName) {
		this.cardSubclassName = cardSubclassName;
	}

	public String getCardClass() {
		return cardClass;
	}

	public void setCardClass(String cardClass) {
		this.cardClass = cardClass;
	}

	public String getBinNo() {
		return binNo;
	}

	public void setBinNo(String binNo) {
		this.binNo = binNo;
	}

	public String getCardIssuer() {
		return cardIssuer;
	}

	public void setCardIssuer(String cardIssuer) {
		this.cardIssuer = cardIssuer;
	}

	public BigDecimal getFaceValue() {
		return faceValue;
	}

	public void setFaceValue(BigDecimal faceValue) {
		this.faceValue = faceValue;
	}

	public String getMembLevel() {
		return membLevel;
	}

	public void setMembLevel(String membLevel) {
		this.membLevel = membLevel;
	}

	public BigDecimal getPtOpencard() {
		return ptOpencard;
	}

	public void setPtOpencard(BigDecimal ptOpencard) {
		this.ptOpencard = ptOpencard;
	}

	public String getMembClass() {
		return membClass;
	}

	public void setMembClass(String membClass) {
		this.membClass = membClass;
	}

	public String getDiscntClass() {
		return discntClass;
	}

	public void setDiscntClass(String discntClass) {
		this.discntClass = discntClass;
	}

	public String getFrequencyClass() {
		return frequencyClass;
	}

	public void setFrequencyClass(String frequencyClass) {
		this.frequencyClass = frequencyClass;
	}

	public String getPtClass() {
		return ptClass;
	}

	public void setPtClass(String ptClass) {
		this.ptClass = ptClass;
	}

	public String getCouponClass() {
		return couponClass;
	}

	public void setCouponClass(String couponClass) {
		this.couponClass = couponClass;
	}

	public BigDecimal getCardPrice() {
		return cardPrice;
	}

	public void setCardPrice(BigDecimal cardPrice) {
		this.cardPrice = cardPrice;
	}

	public BigDecimal getBuyPriceMax() {
		return buyPriceMax;
	}

	public void setBuyPriceMax(BigDecimal buyPriceMax) {
		this.buyPriceMax = buyPriceMax;
	}

	public BigDecimal getBuyPriceMin() {
		return buyPriceMin;
	}

	public void setBuyPriceMin(BigDecimal buyPriceMin) {
		this.buyPriceMin = buyPriceMin;
	}

	public BigDecimal getChkPwd() {
		return chkPwd;
	}

	public void setChkPwd(BigDecimal chkPwd) {
		this.chkPwd = chkPwd;
	}

	public String getPwdType() {
		return pwdType;
	}

	public void setPwdType(String pwdType) {
		this.pwdType = pwdType;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public BigDecimal getExtenUlimit() {
		return extenUlimit;
	}

	public void setExtenUlimit(BigDecimal extenUlimit) {
		this.extenUlimit = extenUlimit;
	}

	public BigDecimal getExtenLlimit() {
		return extenLlimit;
	}

	public void setExtenLlimit(BigDecimal extenLlimit) {
		this.extenLlimit = extenLlimit;
	}

	public BigDecimal getChkPfcard() {
		return chkPfcard;
	}

	public void setChkPfcard(BigDecimal chkPfcard) {
		this.chkPfcard = chkPfcard;
	}

	public BigDecimal getAutoCancelFlag() {
		return autoCancelFlag;
	}

	public void setAutoCancelFlag(BigDecimal autoCancelFlag) {
		this.autoCancelFlag = autoCancelFlag;
	}

	public BigDecimal getCreditUlimit() {
		return creditUlimit;
	}

	public void setCreditUlimit(BigDecimal creditUlimit) {
		this.creditUlimit = creditUlimit;
	}

	public BigDecimal getChargeUlimit() {
		return chargeUlimit;
	}

	public void setChargeUlimit(BigDecimal chargeUlimit) {
		this.chargeUlimit = chargeUlimit;
	}

	public BigDecimal getConsmUlimit() {
		return consmUlimit;
	}

	public void setConsmUlimit(BigDecimal consmUlimit) {
		this.consmUlimit = consmUlimit;
	}

	public Short getEffPeriod() {
		return effPeriod;
	}

	public void setEffPeriod(Short effPeriod) {
		this.effPeriod = effPeriod;
	}

	public Short getExtenPeriod() {
		return extenPeriod;
	}

	public void setExtenPeriod(Short extenPeriod) {
		this.extenPeriod = extenPeriod;
	}

	public Short getExtenMaxcnt() {
		return extenMaxcnt;
	}

	public void setExtenMaxcnt(Short extenMaxcnt) {
		this.extenMaxcnt = extenMaxcnt;
	}

	public String getMustExpirDate() {
		return mustExpirDate;
	}

	public void setMustExpirDate(String mustExpirDate) {
		this.mustExpirDate = mustExpirDate;
	}

	public String getCardTypeName() {
		return cardTypeName;
	}

	public void setCardTypeName(String cardTypeName) {
		this.cardTypeName = cardTypeName;
	}

	public String getExpirMthd() {
		return expirMthd;
	}

	public void setExpirMthd(String expirMthd) {
		this.expirMthd = expirMthd;
	}

	public String getExpirDate() {
		return expirDate;
	}

	public void setExpirDate(String expirDate) {
		this.expirDate = expirDate;
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

	public String getIcType() {
		return icType;
	}

	public void setIcType(String icType) {
		this.icType = icType;
	}

	public String getIcModelNo() {
		return icModelNo;
	}

	public void setIcModelNo(String icModelNo) {
		this.icModelNo = icModelNo;
	}

	public String getChangeFaceValue() {
		return changeFaceValue;
	}

	public void setChangeFaceValue(String changeFaceValue) {
		this.changeFaceValue = changeFaceValue;
	}
	

	public String getDepositFlag() {
		return depositFlag;
	}

	public void setDepositFlag(String depositFlag) {
		this.depositFlag = depositFlag;
	}

	public String getIsChgPwd() {
		return isChgPwd;
	}

	public void setIsChgPwd(String isChgPwd) {
		this.isChgPwd = isChgPwd;
	}

	public String getEcouponType() {
		return ecouponType;
	}

	public void setEcouponType(String ecouponType) {
		this.ecouponType = ecouponType;
	}

	


	
	
}