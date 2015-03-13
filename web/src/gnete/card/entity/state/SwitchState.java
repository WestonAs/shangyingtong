package gnete.card.entity.state;

import java.util.HashMap;
import java.util.Map;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

/**
 * @File:SwitchState.java
 *
 * @description: 结算处理状态
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-lib
 * @version: 1.0
 * @since 1.0 2010-10-19
 */
public class SwitchState extends AbstractState{

	public static final Map ALL = new HashMap();
	
	public static final SwitchState INITIAL = new SwitchState("初始", "0");
	public static final SwitchState SUCCESS = new SwitchState("成功 ", "2");	
	public static final SwitchState FAILURE = new SwitchState("失败 ", "3");
	
	protected SwitchState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static SwitchState valueOf(String value) {
		SwitchState type = (SwitchState) ALL.get(value);
		if (type == null) {
			throw new RuntimeBizException("流程状态错误！");
		}
		return type;
	}

}
