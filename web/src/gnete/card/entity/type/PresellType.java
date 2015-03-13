package gnete.card.entity.type;

import java.util.HashMap;
import java.util.Map;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

/**
 * @File: PresellState.java
 *
 * @description: 预售标识
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-bey
 * @version: 1.0
 * @since 1.0 2010-7-27
 */
public class PresellType extends AbstractState {
	public static final Map ALL = new HashMap();

	public static final PresellType REALSELL = new PresellType("实时售卡", "0");
	public static final PresellType PRESELL = new PresellType("预售卡 ", "1");	
	
	@SuppressWarnings("unchecked")
	protected PresellType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static PresellType valueOf(String value) {
		PresellType type = (PresellType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("流程状态错误！");
		}

		return type;
	}
}
