package gnete.card.entity;

/**
 * <li>微信平台通用数据对象</li>
 * @File: WxCommonDataBean.java
 *
 * @copyright: (c) 2010 ITECH INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2014-1-16 上午10:47:13
 */
public class WxCommonDataBean {

	/** 卡号 */
	private String cardId;
	
	/** 商盈通系统账户ID */
	private String acctId;
	
	/** 发卡机构号 */
	private String cardIssuer;
	
	/** 外部卡号 */
	private String extCardId;
	
	/** 平台用户标识 （就是 WX_USER_INFO表的主键）*/
	private String userId;
	
	private WxUserInfo userInfo;
	
	private CardInfo cardInfo;

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

	public String getCardIssuer() {
		return cardIssuer;
	}

	public void setCardIssuer(String cardIssuer) {
		this.cardIssuer = cardIssuer;
	}

	public String getExtCardId() {
		return extCardId;
	}

	public void setExtCardId(String extCardId) {
		this.extCardId = extCardId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public WxUserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(WxUserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public CardInfo getCardInfo() {
		return cardInfo;
	}

	public void setCardInfo(CardInfo cardInfo) {
		this.cardInfo = cardInfo;
	}
}
