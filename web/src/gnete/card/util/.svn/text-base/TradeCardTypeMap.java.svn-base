package gnete.card.util;

import gnete.card.entity.type.CardType;
import gnete.card.entity.type.InsServiceType;

import java.util.HashMap;
import java.util.Map;

/**
 * @File: TradeCardTypeMap.java
 *
 * @description: 卡种与业务权限表的权限类型转换
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2011-7-1
 */
public class TradeCardTypeMap {

	public static String getInsServiceTradeType(String value){
		if (CardType.ALL.get(value) == null) {
			return null;
		}
		
		Map<String, String> cardTypeMap = new HashMap<String, String>();
		
		cardTypeMap.put(CardType.DEPOSIT.getValue(), InsServiceType.DEPOSIT.getValue());
		cardTypeMap.put(CardType.DISCNT.getValue(), InsServiceType.DISCNT.getValue());
		cardTypeMap.put(CardType.MEMB.getValue(), InsServiceType.MEMB.getValue());
		cardTypeMap.put(CardType.COUPON.getValue(), InsServiceType.COUPON.getValue());
		cardTypeMap.put(CardType.ACCU.getValue(), InsServiceType.ACCU.getValue());
		cardTypeMap.put(CardType.POINT.getValue(), InsServiceType.POINT.getValue());
		cardTypeMap.put(CardType.VIRTUAL.getValue(), InsServiceType.VIRTUAL.getValue());
		
		return cardTypeMap.get(value);
	}
}
