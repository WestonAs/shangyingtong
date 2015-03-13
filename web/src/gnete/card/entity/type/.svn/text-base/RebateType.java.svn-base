package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * @author aps-bey
 */
public class RebateType extends AbstractType {
	public static final Map ALL = new HashMap();

	
	/*
	返利类型代码:
		0折现
		1分摊
		2返利指定卡
		3手工
	*/	
	public static final RebateType CASH	 = new RebateType("折现", "0");
	public static final RebateType SHARE = new RebateType("平摊", "1");
	public static final RebateType CARD = new RebateType("返利指定卡", "2");
	public static final RebateType MANUAL = new RebateType("手工", "3");
	public static final RebateType CARDS = new RebateType("返利多张卡", "4");
	
	@SuppressWarnings("unchecked")
	protected RebateType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static RebateType valueOf(String value) {
		RebateType type = (RebateType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的类型");
		}

		return type;
	}
	
	public static List getAll() {
		return getOrderedList(RebateType.ALL);
	}
	
	public static List getForSellSingle() {
		Map params = new HashMap();
		params.put(CASH.getValue(), CASH);
		params.put(SHARE.getValue(), SHARE);
		return getOrderedList(params);
	}
	
	public static List getForSellBatchFile() {
		Map params = new HashMap();
		params.put(CASH.getValue(), CASH);
		params.put(SHARE.getValue(), SHARE);
		params.put(CARD.getValue(), CARD);
		return getOrderedList(params);
	}

	public static List getForSellBatch() {
		Map params = new HashMap();
		params.put(CASH.getValue(), CASH);
		params.put(SHARE.getValue(), SHARE);
		params.put(CARD.getValue(), CARD);
		params.put(CARDS.getValue(), CARDS);
		return getOrderedList(params);
	}
	
}
