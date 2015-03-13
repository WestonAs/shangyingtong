package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * @File: DateType.java
 *
 * @description: 日期类型
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2011-3-28
 */
public class DateType extends AbstractType {
	public static final Map ALL = new HashMap();

	
	/*
	日期类型代码:
		1 按月
		2 按周
	*/	
	public static final DateType MONTH = new DateType("按月", "1");
	public static final DateType WEEK = new DateType("按周", "2");
	
	@SuppressWarnings("unchecked")
	protected DateType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static DateType valueOf(String value) {
		DateType type = (DateType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的类型");
		}
		return type;
	}
	
	public static List getAll() {
		return getOrderedList(DateType.ALL);
	}
	
}
