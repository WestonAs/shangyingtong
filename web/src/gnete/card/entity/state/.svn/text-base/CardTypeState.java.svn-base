package gnete.card.entity.state;

import java.util.HashMap;
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
public class CardTypeState extends AbstractState {
	public static final Map ALL = new HashMap();

	public static final CardTypeState NORMAL = new CardTypeState("正常", "00");	
	public static final CardTypeState CANCEL = new CardTypeState("注销", "10");	
	
	@SuppressWarnings("unchecked")
	protected CardTypeState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static CardTypeState valueOf(String value) {
		CardTypeState type = (CardTypeState) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("流程状态错误！");
		}

		return type;
	}
	
	public static List getList() {
		return getOrderedList(ALL);
	}
}
