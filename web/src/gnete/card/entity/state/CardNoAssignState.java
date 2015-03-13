package gnete.card.entity.state;

import java.util.HashMap;
import java.util.Map;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

/**
 * @File: CardNoAssignState.java
 *
 * @description: 发卡机构卡号分配状态
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-zwi
 * @version: 1.0
 * @since 1.0 2010-8-5
 */
public class CardNoAssignState extends AbstractState {
	public static final Map ALL = new HashMap();

	public static final CardNoAssignState NORMAL = new CardNoAssignState("正常使用", "00");	
	public static final CardNoAssignState UNUSED = new CardNoAssignState("未启用", "10");
	public static final CardNoAssignState STOPED = new CardNoAssignState("停用", "20");
	
	@SuppressWarnings("unchecked")
	protected CardNoAssignState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static CardNoAssignState valueOf(String value) {
		CardNoAssignState type = (CardNoAssignState) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("流程状态错误！");
		}

		return type;
	}
}
