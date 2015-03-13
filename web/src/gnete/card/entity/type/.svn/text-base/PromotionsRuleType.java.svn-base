package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * @File: PromotionsRuleType.java
 *
 * @description: 促销规则类型代码
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-zwi
 * @version: 1.0
 * @since 1.0 2010-8-16
 */
public class PromotionsRuleType extends AbstractType{
	
	public static final Map ALL = new HashMap();
	
	/*
		01：按倍率送积分（积分子类型见子类型参数，倍率见倍率参数）
		02：送固定积分（积分子类型见子类型参数，固定分值见积分参数）
		04: 金额每满一定金额(金额参数1)就送固定积分（积分子类型见子类型参数，分值见积分参数）
		06：N倍协议积分(倍率参数)
		
		11：商家消费打折(非排他折扣，折扣率见倍率参数)
		12：发卡方打折(非排他折扣，折扣率见倍率参数)
		13：商家消费打折(排他折扣，折扣率见倍率参数)
		14：发卡方打折(排他折扣，折扣率见倍率参数)
		15：事后返还折扣（排他折扣）
		
		21：额外派送赠券（赠券子类型见子类型参数，数量见金额参数2）
		22：按倍率派送赠券（赠券子类型见子类型参数，倍率见倍率参数）
		23：金额每满一定金额(金额参数1)就派送赠券（赠券子类型见子类型参数，数量见金额参数2）

	*/
	public static final PromotionsRuleType SEND_POINTS_RATE = new PromotionsRuleType("按倍率送积分", "01");
	public static final PromotionsRuleType SEND_POINTS_FIXED = new PromotionsRuleType("送固定积分", "02");
	public static final PromotionsRuleType SEND_POINTS_OVER = new PromotionsRuleType("每满一定额度送固定积分", "04");
	public static final PromotionsRuleType PACT_POINTS_RATE = new PromotionsRuleType("N倍协议积分", "06");
	
	public static final PromotionsRuleType DISCOUNT_NO_MERCHANT = new PromotionsRuleType("商家消费打折(非排他折扣)", "11");
	public static final PromotionsRuleType DISCOUNT_NO_CARD = new PromotionsRuleType("发卡方打折(非排他折扣)", "12");
	public static final PromotionsRuleType DISCOUNT_MERCHANT = new PromotionsRuleType("商家消费打折(排他折扣)", "13");
	public static final PromotionsRuleType DISCOUNT_CARD = new PromotionsRuleType("发卡方打折(排他折扣)", "14");
//	public static final PromotionsRuleType LATER_RETURN = new PromotionsRuleType("事后返还（排他折扣）", "15");
	
	public static final PromotionsRuleType DELIVERY_ADDITIONAL = new PromotionsRuleType("额外派送赠券", "21");
	public static final PromotionsRuleType DELIVERY_COUPON = new PromotionsRuleType("按倍率派送赠券", "22");
	public static final PromotionsRuleType DELIVERY_AMOUNT = new PromotionsRuleType("每满一定额度送赠券", "23");
	
	@SuppressWarnings("unchecked")
	protected PromotionsRuleType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static PromotionsRuleType valueOf(String value) {
		PromotionsRuleType type = (PromotionsRuleType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的卡类型");
		}
		return type;
	}
	
	/**
	 * 取得所有促销活动规则类型
	 */
	public static List getAll(){
		return getOrderedList(PromotionsRuleType.ALL);
	}
	
	/**
	 * 取得协议积分规则类型
	 */
	public static List getProtocolRuleList(){
		Map params = new HashMap();
		params.put(SEND_POINTS_RATE.getValue(), SEND_POINTS_RATE);
		params.put(SEND_POINTS_FIXED.getValue(), SEND_POINTS_FIXED);
		params.put(SEND_POINTS_OVER.getValue(), SEND_POINTS_OVER);
		return getOrderedList(params);
	}
	
}
