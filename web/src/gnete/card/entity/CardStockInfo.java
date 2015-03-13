package gnete.card.entity;

import gnete.card.entity.state.CardStockState;
import gnete.card.entity.type.CardType;

public class CardStockInfo {
	private Long id;

	private String cardId;

	private String cardClass;

	private String cardSubclass;

	private String cardBin;

	private String makeId;

	private String cardIssuer;

	private String appOrgId;

	private String appDate;

	private String cardStatus;
	
	private String cardTypeName;
	
	//新增数据库字段.卡上次所属机构号
	private String cardSectorId;
	
	//领卡批次
	private Long appRegId;

	/** 
	 * 是否是 售卡中 或 预售出  状态
	 */
	public boolean isSoldingOrPresoldStatus(){
		return CardStockState.SOLD_ING.getValue().equals(getCardStatus())
				|| CardStockState.PRE_SOLD.getValue().equals(getCardStatus());
	}
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public void setCardClass(String cardClass) {
		this.cardClass = cardClass;
	}

	public String getCardSubclass() {
		return cardSubclass;
	}

	public void setCardSubclass(String cardSubclass) {
		this.cardSubclass = cardSubclass;
	}

	public String getCardBin() {
		return cardBin;
	}

	public void setCardBin(String cardBin) {
		this.cardBin = cardBin;
	}

	public String getMakeId() {
		return makeId;
	}

	public void setMakeId(String makeId) {
		this.makeId = makeId;
	}

	public String getCardIssuer() {
		return cardIssuer;
	}

	public void setCardIssuer(String cardIssuer) {
		this.cardIssuer = cardIssuer;
	}

	public String getAppOrgId() {
		return appOrgId;
	}

	public void setAppOrgId(String appOrgId) {
		this.appOrgId = appOrgId;
	}

	public String getAppDate() {
		return appDate;
	}

	public void setAppDate(String appDate) {
		this.appDate = appDate;
	}

	public String getCardStatus() {
		return cardStatus;
	}

	public void setCardStatus(String cardStatus) {
		this.cardStatus = cardStatus;
	}

	public String getCardTypeName() {
		return cardTypeName;
	}

	public void setCardTypeName(String cardTypeName) {
		this.cardTypeName = cardTypeName;
	}
	
	/**
	 * 卡库存状态名
	 * @return
	 */
	public String getCardStatusName() {
		return CardStockState.ALL.get(cardStatus) == null ? "" : CardStockState
				.valueOf(cardStatus).getName();
	}
	
	/**
	 * 卡种类名
	 * @return
	 */
	public String getCardClassName() {
		return CardType.ALL.get(this.cardClass) == null ? "" : CardType.valueOf(this.cardClass).getName();
	}

	public String getCardSectorId() {
		return cardSectorId;
	}

	public void setCardSectorId(String cardSectorId) {
		this.cardSectorId = cardSectorId;
	}

	public Long getAppRegId() {
		return appRegId;
	}

	public void setAppRegId(Long appRegId) {
		this.appRegId = appRegId;
	}
}