package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * @File: DSetTransType.java
 * 
 * @description: 日结算汇总交易类型
 * 
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2010-11-23
 */
public class DSetTransType extends AbstractType {
	public static final Map ALL = new HashMap();

	public static final DSetTransType CONSUME = new DSetTransType("消费交易", "1");
	public static final DSetTransType INTEGRAL = new DSetTransType("积分交易", "2");
	public static final DSetTransType TIMECARD = new DSetTransType("次卡交易", "3");
	public static final DSetTransType PTEXCGIFT = new DSetTransType("积分兑换礼品", "4");
	public static final DSetTransType ACTIVITY = new DSetTransType("活动", "5");
	public static final DSetTransType SALECARD = new DSetTransType("售卡", "6");
	public static final DSetTransType CASH = new DSetTransType("现金充值", "7");
	public static final DSetTransType BRUSH = new DSetTransType("刷卡充值", "8");
	public static final DSetTransType RETGOODS = new DSetTransType("退货", "9");
	public static final DSetTransType RETRANS = new DSetTransType("补帐交易", "a");
	public static final DSetTransType ADJUST = new DSetTransType("调帐交易", "b");
	public static final DSetTransType ACT_SELLCARD = new DSetTransType("预售卡激活", "c");
	public static final DSetTransType ACT_DEPOSIT = new DSetTransType("预充值激活", "d");
	public static final DSetTransType DEPOSIT_CANCEL = new DSetTransType("充值撤销", "e");
	public static final DSetTransType ECASH_DEPOSIT = new DSetTransType("电子现金充值", "f");
	public static final DSetTransType ECASH_CONSUME= new DSetTransType("电子现金消费", "g");
	public static final DSetTransType EASY_PAYMENT= new DSetTransType("便民缴费", "h");
	public static final DSetTransType NET_BANK_DEPOSIT= new DSetTransType("网上银行卡充值", "i");
	public static final DSetTransType CONSUME_PRESENT = new DSetTransType("消费赠送", "A");

	public static DSetTransType valueOf(String value) {
		DSetTransType type = (DSetTransType) ALL.get(value);

		if (type == null) {
			throw new RuntimeBizException("没有该交易类型");
		}
		return type;
	}

	@SuppressWarnings("unchecked")
	protected DSetTransType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	/**
	 * 取得售卡充值时的交易类型
	 */
	public static List getSellCardTypeList() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(SALECARD.getValue(), SALECARD);
		map.put(CASH.getValue(), CASH);
		map.put(BRUSH.getValue(), BRUSH);
		
		map.put(ACT_SELLCARD.getValue(), ACT_SELLCARD);
		map.put(ACT_DEPOSIT.getValue(), ACT_DEPOSIT);
		map.put(DEPOSIT_CANCEL.getValue(), DEPOSIT_CANCEL);
		map.put(ECASH_DEPOSIT.getValue(), ECASH_DEPOSIT);
		
		map.put(NET_BANK_DEPOSIT.getValue(), NET_BANK_DEPOSIT);
		
		return getOrderedList(map);
	}

	/**
	 * 取得交易清算时的交易类型
	 */
	public static List getTransSetTypeList() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(CONSUME.getValue(), CONSUME);
		map.put(INTEGRAL.getValue(), INTEGRAL);
		map.put(TIMECARD.getValue(), TIMECARD);
		map.put(PTEXCGIFT.getValue(), PTEXCGIFT);
		
		map.put(RETGOODS.getValue(), RETGOODS);
		map.put(RETRANS.getValue(), RETRANS);
		map.put(ADJUST.getValue(), ADJUST);
		
		map.put(ECASH_CONSUME.getValue(), ECASH_CONSUME);
		map.put(EASY_PAYMENT.getValue(), EASY_PAYMENT);
		
		map.put(CONSUME_PRESENT.getValue(), CONSUME_PRESENT);
		
		return getOrderedList(map);
	}
	
	public static List getDsetTransTypeList() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(CONSUME.getValue(), CONSUME);
		map.put(INTEGRAL.getValue(), INTEGRAL);
		map.put(TIMECARD.getValue(), TIMECARD);
		map.put(PTEXCGIFT.getValue(), PTEXCGIFT);
		map.put(SALECARD.getValue(), new DSetTransType("售卡充值", "6"));
		return getOrderedList(map);
	}
	
	public static List getList() {
		return getOrderedList(ALL);
	}
}
