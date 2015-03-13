package gnete.card.entity;

import gnete.card.entity.flag.ReceiveFlag;
import gnete.card.entity.state.CheckState;
import gnete.card.entity.type.AppOrgType;
import gnete.card.entity.type.CardType;

import java.math.BigDecimal;
import java.util.Date;

public class AppReg {
	private Long id;

	private String appDate;

	private String cardSubClass;

	private String cardIssuer;

	private String strNo;

	private Integer cardNum;

	private String checkStrNo;

	private Integer checkCardNum;

	private String appOrgType;

	private String appOrgId;

	private String flag;

	private String linkMan;

	private String contact;

	private String address;

	private String memo;

	private String status;

	private String updateBy;

	private Date updateTime;
	
	/** 结束卡号 */
	private String endCardId;
	
	//新增数据库字段.领卡时是卡的领出机构号，返库是卡的返入机构号
	private String cardSectorId;
	
	private String cardStockStatus; // 卡库存状态

	/** 申购单号 */
	private String orderNo;
	/** 申购单位 */
	private String orderUnit;
	/** 身份证号 */
	private String identityCard;
	
	/** 单张卡面值 （卡子类型表的字段）*/
	private BigDecimal faceValue;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAppDate() {
		return appDate;
	}

	public void setAppDate(String appDate) {
		this.appDate = appDate;
	}

	public String getCardSubClass() {
		return cardSubClass;
	}

	public void setCardSubClass(String cardSubClass) {
		this.cardSubClass = cardSubClass;
	}

	public String getCardIssuer() {
		return cardIssuer;
	}

	public void setCardIssuer(String cardIssuer) {
		this.cardIssuer = cardIssuer;
	}

	public String getStrNo() {
		return strNo;
	}

	public void setStrNo(String strNo) {
		this.strNo = strNo;
	}

	public Integer getCardNum() {
		return cardNum;
	}

	public void setCardNum(Integer cardNum) {
		this.cardNum = cardNum;
	}

	public String getAppOrgId() {
		return appOrgId;
	}

	public void setAppOrgId(String appOrgId) {
		this.appOrgId = appOrgId;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
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

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
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

	public String getCheckStrNo() {
		return checkStrNo;
	}

	public void setCheckStrNo(String checkStrNo) {
		this.checkStrNo = checkStrNo;
	}

	public Integer getCheckCardNum() {
		return checkCardNum;
	}

	public void setCheckCardNum(Integer checkCardNum) {
		this.checkCardNum = checkCardNum;
	}

	public String getAppOrgType() {
		return appOrgType;
	}

	public void setAppOrgType(String appOrgType) {
		this.appOrgType = appOrgType;
	}

	/**
	 * 取得卡种名
	 */
	public String getCardTypeName() {
		String cardType = this.strNo.substring(9, 11);
		return CardType.ALL.get(cardType) == null ? "" : CardType.valueOf(
				cardType).getName();
	}

	/**
	 * 状态名
	 */
	public String getStatusName() {
		return CheckState.ALL.get(status) == null ? "" : CheckState.valueOf(
				status).getName();
	}

	/**
	 * 发生方式名
	 */
	public String getFlagName() {
		return ReceiveFlag.ALL.get(flag) == null ? "" : ReceiveFlag.valueOf(
				flag).getName();
	}

	/**
	 * 领卡机构类型名
	 */
	public String getAppOrgTypeName() {
		return AppOrgType.ALL.get(appOrgType) == null ? "" : AppOrgType
				.valueOf(appOrgType).getName();
	}

	public String getCardSectorId() {
		return cardSectorId;
	}

	public void setCardSectorId(String cardSectorId) {
		this.cardSectorId = cardSectorId;
	}

	public String getCardStockStatus() {
		return cardStockStatus;
	}

	public void setCardStockStatus(String cardStockStatus) {
		this.cardStockStatus = cardStockStatus;
	}

	public String getEndCardId() {
		return endCardId;
	}

	public void setEndCardId(String endCardId) {
		this.endCardId = endCardId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderUnit() {
		return orderUnit;
	}

	public void setOrderUnit(String orderUnit) {
		this.orderUnit = orderUnit;
	}

	public String getIdentityCard() {
		return identityCard;
	}

	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}

	public BigDecimal getFaceValue() {
		return faceValue;
	}

	public void setFaceValue(BigDecimal faceValue) {
		this.faceValue = faceValue;
	}
	
}