package gnete.card.entity.flag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

/**
 * @File: TrueOrFalseFlag.java
 * 
 * @description: 是否启用密码
 * 
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-zwi
 * @version: 1.0
 * @since 1.0 2010-8-2
 */
public class TrueOrFalseFlag extends AbstractState {
	public static final Map ALL = new HashMap();

	public static final TrueOrFalseFlag TRUE = new TrueOrFalseFlag("是", "1");
	public static final TrueOrFalseFlag FALSE = new TrueOrFalseFlag("否", "0");

	@SuppressWarnings("unchecked")
	protected TrueOrFalseFlag(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static TrueOrFalseFlag valueOf(String value) {
		TrueOrFalseFlag type = (TrueOrFalseFlag) ALL.get(value);

		if (type == null) {
			throw new RuntimeBizException("流程状态错误！");
		}

		return type;
	}

	public static List getAll() {
		return getOrderedList(TrueOrFalseFlag.ALL);
	}
}
