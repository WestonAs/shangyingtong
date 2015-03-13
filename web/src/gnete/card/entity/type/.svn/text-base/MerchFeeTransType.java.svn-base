package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * 发卡机构与商户手续费费率参数交易类型
 * @author aps-lib
 */
public class MerchFeeTransType extends AbstractType {
	public static final Map ALL = new HashMap();

	/*
	 * 1 消费交易、  2 通用积分消费、3 次卡消费、 4 积分兑换礼品、 5 专属积分消费、 6 折扣功能
	 * A 消费赠送
	 */
	
	public static final MerchFeeTransType CONSUME = new MerchFeeTransType("消费交易", "1");
	public static final MerchFeeTransType COMMON_PT_CONSULE = new MerchFeeTransType("通用积分消费", "2");
	public static final MerchFeeTransType TIME_CARD_CONSULE = new MerchFeeTransType("次卡消费", "3");
	public static final MerchFeeTransType PT_EXC_GIFT_CONSULE = new MerchFeeTransType("积分兑换礼品", "4");
	public static final MerchFeeTransType SPECIAL_PT_CONSULE = new MerchFeeTransType("专属积分消费", "5");
	public static final MerchFeeTransType DISCOUNT_FUN = new MerchFeeTransType("折扣功能", "6");
	public static final MerchFeeTransType CONSUME_PRESENT = new MerchFeeTransType("消费赠送", "A");
	
	public static MerchFeeTransType valueOf(String value) {
		MerchFeeTransType type = (MerchFeeTransType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("没有该交易类型");
		}

		return type;
	}
	
	@SuppressWarnings("unchecked")
	protected MerchFeeTransType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static List getList() {
		return getOrderedList(ALL);
	}
	
}
