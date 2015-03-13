package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * @File: AmtType.java
 *
 * @description: 金额类型
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-zwi
 * @version: 1.0
 * @since 1.0 2010-8-16
 */
public class MerchTransType extends AbstractType{
	
	public static final Map ALL = new HashMap();
	
	/*
	 	1 消费交易  
	 	2 积分交易  
	 	3 次卡交易 
	 	4 赠送通用积分 
	 	5 赠送礼品 
	 	6 售卡 
	 	7 充值
	*/
	public static final MerchTransType CONSUME = new MerchTransType("消费交易", "1");
	public static final MerchTransType INTEGRATION = new MerchTransType("积分交易", "2");
	public static final MerchTransType ACCUCARD = new MerchTransType("次卡交易", "3");
	public static final MerchTransType PRESENTCOMMON = new MerchTransType("赠送通用积分", "4");
	public static final MerchTransType PRESENTGIFT = new MerchTransType("赠送礼品", "5");
	public static final MerchTransType SALECARD = new MerchTransType("售卡 ", "6");
	public static final MerchTransType CHARGE = new MerchTransType("充值", "7");
	
	@SuppressWarnings("unchecked")
	protected MerchTransType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static MerchTransType valueOf(String value) {
		MerchTransType type = (MerchTransType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的卡类型");
		}
		return type;
	}
	
	public static List getAll(){
		return getOrderedList(MerchTransType.ALL);
	}
	
	public static List getMerchTransType(){
		Map params = new HashMap();
		params.put(CONSUME.getValue(), CONSUME);
		params.put(INTEGRATION.getValue(), INTEGRATION);
		params.put(ACCUCARD.getValue(), ACCUCARD);
		params.put(PRESENTCOMMON.getValue(), PRESENTCOMMON);
		params.put(PRESENTGIFT.getValue(), PRESENTGIFT);
		return getOrderedList(params);
	}
	
	public static List getCardSaleTransType(){
		Map params = new HashMap();
		params.put(SALECARD.getValue(), SALECARD);
		params.put(CHARGE.getValue(), CHARGE);
		return getOrderedList(params);
	}
	
}
