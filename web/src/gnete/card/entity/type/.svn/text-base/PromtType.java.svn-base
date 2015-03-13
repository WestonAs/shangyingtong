package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * @File: PromtType.java
 *
 * @description: 活动类型
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-zwi
 * @version: 1.0
 * @since 1.0 2010-10-11
 */
public class PromtType extends AbstractType{

	public static final Map ALL = new HashMap();
	
	/**
	 * 活动类型 0 活动 1协议
	 */
	public static final PromtType ACTIVITY = new PromtType("活动","0");
	
	/**
	 * 活动类型 0 活动 1协议
	 */
	public static final PromtType PROTOCOL = new PromtType("协议","1");
	
	protected PromtType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static PromtType valueOf(String value) {
		PromtType type = (PromtType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的类型");
		}
		return type;
	}
	
	public static List getAll(){
		return getOrderedList(PromtType.ALL);
	}
	
}
