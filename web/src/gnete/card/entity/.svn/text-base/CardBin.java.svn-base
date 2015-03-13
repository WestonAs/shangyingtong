package gnete.card.entity;

import flink.util.SpringContext;
import gnete.card.dao.CardInfoDAO;
import gnete.card.entity.state.CardBinState;

import java.util.Date;

public class CardBin {

	public final static int BINPOS = 4; // 卡BIN起始位置
	public final static int BINSIZE = 6; // 卡BIN长度
	public final static String ERROR = "error"; // 卡BIN错误

	private String binNo;

	private String binName;

	private String cardIssuer;

	private String currCode;

	private String cardType;

	private String status;

	private String updateBy;

	private Date updateTime;

	private String cardTypeName;

	private String cardSubclass;

	public String getBinNo() {
		return binNo;
	}

	public void setBinNo(String binNo) {
		this.binNo = binNo;
	}

	public String getBinName() {
		return binName;
	}

	public void setBinName(String binName) {
		this.binName = binName;
	}

	public String getCardIssuer() {
		return cardIssuer;
	}

	public void setCardIssuer(String cardIssuer) {
		this.cardIssuer = cardIssuer;
	}

	public String getCurrCode() {
		return currCode;
	}

	public void setCurrCode(String currCode) {
		this.currCode = currCode;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
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

	public String getCardTypeName() {
		return cardTypeName;
	}

	public void setCardTypeName(String cardTypeName) {
		this.cardTypeName = cardTypeName;
	}

	/**
	 * 状态名
	 * @return
	 */
	public String getStatusName() {
		return CardBinState.ALL.get(this.status) == null ? "" : CardBinState
				.valueOf(this.status).getName();
	}

	/**
	 * 根据卡号获取卡BIN
	 * 
	 * @author benyan
	 * @param cardId
	 *            卡号
	 * @return 卡BIN "Error" 返回卡BIN错误
	 */
//	public static String getBinNo(String cardId) {
//		if (cardId.length() < BINPOS + BINSIZE - 1) {
//			return ERROR;
//		} else {
//			return cardId.substring(BINPOS - 1, BINPOS + BINSIZE - 1);
//		}
//	}
	public static String getBinNo(String cardId) {
		CardInfoDAO cardInfoDAO = (CardInfoDAO) SpringContext.getService("cardInfoDAOImpl");
		CardInfo cardInfo = (CardInfo) cardInfoDAO.findByPk(cardId);
		if (cardInfo == null) {
			return ERROR;
		} else {
			return cardInfo.getCardBin();
		}
	}

	public String getCardSubclass() {
		return cardSubclass;
	}

	public void setCardSubclass(String cardSubclass) {
		this.cardSubclass = cardSubclass;
	}

}