package gnete.card.entity.state;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Arrays;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;


/**
 * @File: CustomerRebateState.java
 *
 * @description: 通用状态
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-bey
 * @version: 1.0
 * @since 1.0 2010-8-14
 */
public class CommonState extends AbstractState {
	public static final Map ALL = new HashMap();

	public static final CommonState NORMAL = new CommonState("正常", "00");
	public static final CommonState DISABLE = new CommonState("失效", "10");	
	public static final CommonState USED = new CommonState("已启用", "03");	
	
	@SuppressWarnings("unchecked")
	protected CommonState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static CommonState valueOf(String value) {
		CommonState type = (CommonState) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("状态错误！");
		}

		return type;
	}
	
	public static List getAll(){
		return getOrderedList(CommonState.ALL);
	}
	
	public static List getList() {
		return Arrays.asList(new CommonState[] {NORMAL,DISABLE});
	}

}
