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
public class AmtType extends AbstractType{
	
	public static final Map ALL = new HashMap();
	
	/*
	 	0-交易
		1-清算
	*/
	public static final AmtType TRAN = new AmtType("交易", "00");
	public static final AmtType CLEAR = new AmtType("清算", "01");
	
	@SuppressWarnings("unchecked")
	protected AmtType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static AmtType valueOf(String value) {
		AmtType type = (AmtType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的卡类型");
		}
		return type;
	}
	
	public static List getAll(){
		return getOrderedList(AmtType.ALL);
	}
	
}
