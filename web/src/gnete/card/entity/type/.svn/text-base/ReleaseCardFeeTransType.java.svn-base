package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * @author aps-mjn
 * @modify aps-lib
 */
public class ReleaseCardFeeTransType extends AbstractType {
	public static final Map ALL = new HashMap();

	/*
	 * 01 -- 储值充值; 11 -- 储值消费; 02 -- 次卡充值 ; 12 -- 次卡消费; 03 -- 赠券充值 ; 13 -- 赠券消费;
	 * 04 -- 通用积分充值; 14 -- 通用积分消费; 05 -- 专属积分充值 ; 15 -- 专属积分消费; 16 -- 积分兑换礼品消费; 20 -- 会员数 按月
	 * 21 -- 折扣卡会员数 按月
	 */
	public static final ReleaseCardFeeTransType DEPOSIT = new ReleaseCardFeeTransType("储值充值", "01");
	public static final ReleaseCardFeeTransType CONSULE = new ReleaseCardFeeTransType("储值消费", "11");
	public static final ReleaseCardFeeTransType TIME_CARD_DEPOSIT = new ReleaseCardFeeTransType("次卡充值 ", "02");
	public static final ReleaseCardFeeTransType TIME_CARD_CONSULE = new ReleaseCardFeeTransType("次卡消费", "12");
	public static final ReleaseCardFeeTransType COUPON_DEPOSIT = new ReleaseCardFeeTransType("赠券充值", "03");
	public static final ReleaseCardFeeTransType COUPON_CONSULE = new ReleaseCardFeeTransType("赠券消费", "13");
	public static final ReleaseCardFeeTransType COMMON_PT_DEPOSIT = new ReleaseCardFeeTransType("通用积分充值", "04");
	public static final ReleaseCardFeeTransType COMMON_PT_CONSULE = new ReleaseCardFeeTransType("通用积分消费", "14");
	public static final ReleaseCardFeeTransType SPECIAL_PT_DEPOSIT = new ReleaseCardFeeTransType("专属积分充值", "05");
	public static final ReleaseCardFeeTransType SPECIAL_PT_CONSULE = new ReleaseCardFeeTransType("专属积分消费", "15");
	public static final ReleaseCardFeeTransType PT_EXC_GIFT_CONSULE = new ReleaseCardFeeTransType("积分兑换礼品消费", "16");

	public static final ReleaseCardFeeTransType MEMB_NUM = new ReleaseCardFeeTransType("会员数", "20");
	public static final ReleaseCardFeeTransType DISCOUNT_MEMB_NUM = new ReleaseCardFeeTransType("折扣卡会员数", "21");
	
	public static ReleaseCardFeeTransType valueOf(String value) {
		ReleaseCardFeeTransType type = (ReleaseCardFeeTransType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("没有该交易类型");
		}

		return type;
	}
	
	@SuppressWarnings("unchecked")
	protected ReleaseCardFeeTransType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static List getList() {
		return getOrderedList(ALL);
	}
	
	/** 商户手续费交易类型 */
	public static List getMerchTransType() {
		Map params = new HashMap();
		params.put(CONSULE.getValue(), CONSULE);
		params.put(TIME_CARD_CONSULE.getValue(), TIME_CARD_CONSULE);
		params.put(COUPON_CONSULE.getValue(), COUPON_CONSULE);
		params.put(COMMON_PT_CONSULE.getValue(), COMMON_PT_CONSULE);
		params.put(SPECIAL_PT_CONSULE.getValue(), SPECIAL_PT_CONSULE);
		params.put(PT_EXC_GIFT_CONSULE.getValue(), PT_EXC_GIFT_CONSULE);
		
		return getOrderedList(params);
	}
}
