package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * @File: PayType.java
 *
 * @description: 单机产品缴费付款方式
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @modify:
 * @version: 1.0
 * @since 1.0 2012-3-14
 */
public class PayType extends AbstractType{
	
	public static final Map ALL = new HashMap();
	
	/*
	单机产品缴费付款方式代码:
		00 web、
		01 POS银行卡、
		02 POS实名
	*/
	public static final PayType WEB = new PayType("WEB", "00");
	public static final PayType POS_BANK_ACCT = new PayType("POS银行卡", "01");
	public static final PayType POS_REAL_NAME = new PayType("POS实名", "02");

	protected PayType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static PayType valueOf(String value) {
		PayType type = (PayType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的类型");
		}
		return type;
	}
	
	public static List getAll(){
		return getOrderedList(PayType.ALL);
	}
}
