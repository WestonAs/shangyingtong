package gnete.card.entity.flag;

import java.util.HashMap;
import java.util.Map;
import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

/**
 * @File:DaySwitchFlag.java
 *
 * @description: 处理状态
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-lib
 * @version: 1.0
 * @since 1.0 2011-6-14
 */
public class DaySwitchFlag extends AbstractState{

	public static final Map ALL = new HashMap();
	public static final DaySwitchFlag INITIAL = new DaySwitchFlag("初始", "0");
	public static final DaySwitchFlag DEALING = new DaySwitchFlag("处理中 ", "1");	
	public static final DaySwitchFlag SUCCESS = new DaySwitchFlag("处理完成", "2");
	public static final DaySwitchFlag FAILURE = new DaySwitchFlag("处理失败", "3");
	
	protected DaySwitchFlag(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static DaySwitchFlag valueOf(String value) {
		DaySwitchFlag type = (DaySwitchFlag) ALL.get(value);
		if (type == null) {
			throw new RuntimeBizException("流程状态错误！");
		}
		return type;
	}
}
