package gnete.card.entity.flag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

/**
 * @File: SwitchFlag.java
 * 
 * @description: 日切状态
 * 
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-lib
 * @version: 1.0
 * @since 1.0 2010-10-19
 */
public class SwitchFlag extends AbstractState{

	public static final Map ALL = new HashMap();

	public static final SwitchFlag INITIAL = new SwitchFlag("初始", "0");
	public static final SwitchFlag SUCCESS = new SwitchFlag("日切成功", "2");
	public static final SwitchFlag FAILURE = new SwitchFlag("日切失败", "3");
	
	protected SwitchFlag(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static SwitchFlag valueOf(String value) {
		SwitchFlag type = (SwitchFlag) ALL.get(value);

		if (type == null) {
			throw new RuntimeBizException("流程状态错误！");
		}

		return type;
	}

	public static List getAll() {
		return getOrderedList(SwitchFlag.ALL);
	}

}
