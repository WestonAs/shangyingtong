package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * @File: DrawType.java
 *
 * @description: 范围类型
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-zwi
 * @version: 1.0
 * @since 1.0 2010-8-17
 */
public class ScopeType extends AbstractType{
	
	public static final Map ALL = new HashMap();
	
	/*
	 	1：排除范围
		2：增加范围
		排除和增加二选一
	*/
	public static final ScopeType EXCLUDE = new ScopeType("排除范围", "1");
	public static final ScopeType INCLUDE = new ScopeType("增加范围", "2");
	
	@SuppressWarnings("unchecked")
	protected ScopeType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static ScopeType valueOf(String value) {
		ScopeType type = (ScopeType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的范围类型");
		}
		return type;
	}
	
	public static List getAll(){
		return getOrderedList(ScopeType.ALL);
	}
	
}
