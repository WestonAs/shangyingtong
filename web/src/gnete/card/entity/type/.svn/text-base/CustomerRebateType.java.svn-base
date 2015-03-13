package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * @File: CustomerRebateType.java
 *
 * @description:  客户返利方式
 * 
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-zwi
 * @version: 1.0
 * @since 1.0 2010-10-23
 */
public class CustomerRebateType extends AbstractType {
	public static final Map ALL = new HashMap();

	
	/*
	返利类型代码:
		0指定卡
		1购卡时客户选择
	*/	
	public static final CustomerRebateType SPECIFY_CARD	 = new CustomerRebateType("指定卡", "0");
	public static final CustomerRebateType CUSTOMER_CHOOSE = new CustomerRebateType("客户选择", "1");
	
	@SuppressWarnings("unchecked")
	protected CustomerRebateType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static CustomerRebateType valueOf(String value) {
		CustomerRebateType type = (CustomerRebateType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的类型");
		}

		return type;
	}
	
	public static List getAll() {
		return getOrderedList(CustomerRebateType.ALL);
	}
	
}
