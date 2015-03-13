package gnete.card.entity.state;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

/**
 * @File: CardBINState.java
 *
 * @description: 卡BIN的状态
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-zwi
 * @version: 1.0
 * @since 1.0 2010-7-27
 */
public class CardBinState extends AbstractState {
	public static final Map ALL = new HashMap();

	public static final CardBinState UNUSED = new CardBinState("未启用", "00");
	public static final CardBinState NORMAL = new CardBinState("正常使用", "10");	
	public static final CardBinState STOPED = new CardBinState("停用", "20");
	public static final CardBinState WAITED = new CardBinState("待审核", "30");
	
	@SuppressWarnings("unchecked")
	protected CardBinState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static CardBinState valueOf(String value) {
		CardBinState type = (CardBinState) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("流程状态错误！");
		}

		return type;
	}
	
	public static List getList() {
		return getOrderedList(CardBinState.ALL);
	}
}
