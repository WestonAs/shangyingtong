package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * 发卡机构与商户手续费费率参数交易类型
 * @author aps-mjn
 * @modify aps-lib
 */
public class CardMerchFeeTransType extends AbstractType {
	public static final Map ALL = new HashMap();

	/*
	 * 1 消费交易、  2 通用积分消费、3 次卡消费、 4 积分兑换礼品、 5 专属积分消费、 6 折扣功能
	 * A 消费赠送
	 */
	
	/*public static final CardMerchFeeTransType CONSUME = new CardMerchFeeTransType("消费交易", "1");
	public static final CardMerchFeeTransType INTEGRAL = new CardMerchFeeTransType("积分交易", "2");
	public static final CardMerchFeeTransType TIMECARD = new CardMerchFeeTransType("次卡交易", "3");
	public static final CardMerchFeeTransType PTEXCGIFT = new CardMerchFeeTransType("积分兑换礼品", "4");
	public static final CardMerchFeeTransType ACTIVITY = new CardMerchFeeTransType("活动", "5");
	public static final CardMerchFeeTransType SALECARD = new CardMerchFeeTransType("售卡/充值", "6");
	public static final CardMerchFeeTransType RETGOODS = new CardMerchFeeTransType("退货", "9");*/
	
	public static final CardMerchFeeTransType CONSUME = new CardMerchFeeTransType("消费交易", "1");
	public static final CardMerchFeeTransType COMMON_PT_CONSULE = new CardMerchFeeTransType("通用积分消费", "2");
	public static final CardMerchFeeTransType TIME_CARD_CONSULE = new CardMerchFeeTransType("次卡消费", "3");
	public static final CardMerchFeeTransType PT_EXC_GIFT_CONSULE = new CardMerchFeeTransType("积分兑换礼品", "4");
	public static final CardMerchFeeTransType SPECIAL_PT_CONSULE = new CardMerchFeeTransType("专属积分消费", "5");
	public static final CardMerchFeeTransType DISCOUNT_FUN = new CardMerchFeeTransType("折扣功能", "6");
	public static final CardMerchFeeTransType CONSUME_PRESENT = new CardMerchFeeTransType("消费赠送", "A");
	
	public static CardMerchFeeTransType valueOf(String value) {
		CardMerchFeeTransType type = (CardMerchFeeTransType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("没有该交易类型");
		}

		return type;
	}
	
	@SuppressWarnings("unchecked")
	protected CardMerchFeeTransType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static List getList() {
		return getOrderedList(ALL);
	}
	
	/*public static List getForFee() {
		Map params = new HashMap();
		params.put(CONSUME.getValue(), CONSUME);
		params.put(INTEGRAL.getValue(), INTEGRAL);
		params.put(TIMECARD.getValue(), TIMECARD);
		params.put(PTEXCGIFT.getValue(), PTEXCGIFT);
		params.put(SALECARD.getValue(), SALECARD);
		return getOrderedList(params);
	}
	
	public static List getForMerchFee() {
		Map params = new HashMap();
		params.put(CONSUME.getValue(), CONSUME);
		params.put(INTEGRAL.getValue(), INTEGRAL);
		params.put(TIMECARD.getValue(), TIMECARD);
		params.put(PTEXCGIFT.getValue(), PTEXCGIFT);
		return getOrderedList(params);
	}*/
	
}
