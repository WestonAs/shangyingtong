package gnete.card.entity.state;

import java.util.HashMap;
import java.util.Map;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

/**
 * @File:SetState.java
 *
 * @description: 结算处理状态
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-lib
 * @version: 1.0
 * @since 1.0 2010-10-19
 */
public class SetState extends AbstractState{

	public static final Map ALL = new HashMap();
	public static final SetState INITIAL = new SetState("初始", "0");
	public static final SetState DEALING = new SetState("结算处理中 ", "1");	
	public static final SetState SUCCESS = new SetState("结算处理完成", "2");
	public static final SetState FAILURE = new SetState("结算处理失败", "3");
	
	protected SetState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static SetState valueOf(String value) {
		SetState type = (SetState) ALL.get(value);
		if (type == null) {
			throw new RuntimeBizException("流程状态错误！");
		}
		return type;
	}
	
}
