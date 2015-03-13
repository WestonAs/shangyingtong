package gnete.card.util;

import gnete.card.entity.type.DSetTransType;
import gnete.card.entity.type.TransType;

import java.util.HashMap;
import java.util.Map;

public class TradeTypeMap {

	public static String[] getTransTradeType(String value){
		if (DSetTransType.ALL.get(value) == null) {
			return null;
		}
		
		Map<String, String[]> transMap = new HashMap<String, String[]>();
		transMap.put(DSetTransType.CONSUME.getValue(), new String[]{TransType.NORMAL_CONSUME.getValue(),
			TransType.PART_CONSUME.getValue(), TransType.EXPIRE_CONSUME.getValue(),
			TransType.PRE_AUTH_FINISH.getValue(), TransType.CONSUME_PRESENT.getValue()});
		
		transMap.put(DSetTransType.INTEGRAL.getValue(), new String[]{TransType.POINT_CONSUME.getValue(),
			TransType.POINT_CHANGE.getValue()});
		
		
		transMap.put(DSetTransType.TIMECARD.getValue(), new String[]{TransType.TIME_CARD_CONSUME.getValue()});
		
		transMap.put(DSetTransType.PTEXCGIFT.getValue(), new String[]{TransType.POINT_GIFT.getValue()});
		
		transMap.put(DSetTransType.SALECARD.getValue(), new String[]{TransType.SELL_CARD.getValue()});
		
		transMap.put(DSetTransType.CASH.getValue(), new String[]{TransType.RECHARGE.getValue()});
		
		transMap.put(DSetTransType.BRUSH.getValue(), new String[]{TransType.BRUSH_PECHARGE.getValue()});
		
		transMap.put(DSetTransType.RETRANS.getValue(), new String[]{TransType.CARD_REFUND.getValue()});
		
		transMap.put(DSetTransType.ACT_SELLCARD.getValue(), new String[]{TransType.PRESELL_ACTIVATE.getValue()});
		
		transMap.put(DSetTransType.ACT_DEPOSIT.getValue(), new String[]{TransType.PRECHARGE_ACTIVATE.getValue()});
		
		return transMap.get(value);
	}
	
}
