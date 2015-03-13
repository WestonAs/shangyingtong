package gnete.card.entity.state;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

/**
 * @File: RuleState.java
 * 
 * @description: 规则状态
 * 
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-zwi
 * @version: 1.0
 * @since 1.0 2010-10-10
 */
public class RuleState extends AbstractState {
	public static final Map ALL = new HashMap();

	public static final RuleState INVALID = new RuleState("失效", "00");
	public static final RuleState EFFECT = new RuleState("生效", "01");

	@SuppressWarnings("unchecked")
	protected RuleState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static RuleState valueOf(String value) {
		RuleState type = (RuleState) ALL.get(value);

		if (type == null) {
			throw new RuntimeBizException("流程状态错误！");
		}

		return type;
	}

	public static List getAll() {
		return getOrderedList(RuleState.ALL);
	}
}
