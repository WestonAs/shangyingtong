package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * @File: CycleType.java
 *
 * @description: 周期类型
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: aps-lib
 * @version: 1.0
 * @since 1.0 2011-10-13
 */
public class CycleType extends AbstractType {
	public static final Map ALL = new HashMap();
	
	/*
	周期类型代码:
		1按日
		2按周
		3按月
	*/	
	public static final CycleType DAY = new CycleType("按日", "1");
	public static final CycleType WEEK = new CycleType("按周", "2");
	public static final CycleType MONTH = new CycleType("按月", "3");
	
	@SuppressWarnings("unchecked")
	protected CycleType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static CycleType valueOf(String value) {
		CycleType type = (CycleType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的类型");
		}
		return type;
	}
	
	public static List getAll() {
		return getValueOrderedList(CycleType.ALL);
	}
	
	public static List getDay(){
		Map params = new HashMap();
		params.put(DAY.getValue(), DAY);
		
		return getOrderedList(params);
	}
}
