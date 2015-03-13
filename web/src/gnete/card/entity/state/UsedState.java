package gnete.card.entity.state;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

/**
 * @File: UsedState.java
 *
 * @description: sequence使用状态
 * <li>00: 未使用</li>
 * <li>01: 已使用</li>
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-zwi
 * @version: 1.0
 * @since 1.0 2010-9-30
 */
public class UsedState extends AbstractState {
	public static final Map ALL = new HashMap();

	public static final UsedState UNUSED = new UsedState("未使用", "00");
	public static final UsedState USED = new UsedState("已使用", "01");	
	
	@SuppressWarnings("unchecked")
	protected UsedState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static UsedState valueOf(String value) {
		UsedState type = (UsedState) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("流程状态错误！");
		}

		return type;
	}
	
	public static List getAll(){
		return getOrderedList(UsedState.ALL);
	}
}
