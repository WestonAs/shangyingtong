package gnete.card.entity.state;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

/**
 * @File: CardBINState.java
 *
 * @description: 卡类型的状态
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-zwi
 * @version: 1.0
 * @since 1.0 2010-7-27
 */
public class TransLimitTermStatus extends AbstractState {
	public static final Map ALL = new LinkedHashMap();

	public static final TransLimitTermStatus NORMAL = new TransLimitTermStatus("正常", "01");	
	public static final TransLimitTermStatus CANCEL = new TransLimitTermStatus("注销", "00");	
	
	@SuppressWarnings("unchecked")
	protected TransLimitTermStatus(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static TransLimitTermStatus valueOf(String value) {
		TransLimitTermStatus type = (TransLimitTermStatus) ALL.get(value);
		if (type == null) {
			throw new RuntimeBizException("状态错误！");
		}

		return type;
	}
	
	public static List getList() {
		return getOrderedList(ALL);
	}
}
