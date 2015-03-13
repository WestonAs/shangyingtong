package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * @File: DrawType.java
 *
 * @description: 奖品类型
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-zwi
 * @version: 1.0
 * @since 1.0 2010-8-17
 */
public class PrizeType extends AbstractType{
	
	public static final Map ALL = new HashMap();
	
	/*
	 	00：礼品
		01：积分
		02：赠券
		09：手工操作协助
	*/
	public static final PrizeType PRESENT = new PrizeType("礼品", "00");
	public static final PrizeType POINTS = new PrizeType("积分", "01");
	public static final PrizeType COUPONS = new PrizeType("赠券", "02");
	public static final PrizeType OTHERS = new PrizeType("手工操作协助", "09");
	
	@SuppressWarnings("unchecked")
	protected PrizeType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static PrizeType valueOf(String value) {
		PrizeType type = (PrizeType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的奖品类型");
		}
		return type;
	}
	
	public static List getAll(){
		return getOrderedList(PrizeType.ALL);
	}
	
}
