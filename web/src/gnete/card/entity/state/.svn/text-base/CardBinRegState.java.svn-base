package gnete.card.entity.state;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

/**
 * @File: CardBINState.java
 *
 * @description: 卡BIN申请状态
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-zwi
 * @version: 1.0
 * @since 1.0 2010-7-27
 */
public class CardBinRegState extends AbstractState {
	public static final Map ALL = new HashMap();

	public static final CardBinRegState WAITED = new CardBinRegState("待审核", "00");
	public static final CardBinRegState PASSED = new CardBinRegState("审核通过", "10");	
	public static final CardBinRegState FALURE = new CardBinRegState("审核不通过", "20");
	
	@SuppressWarnings("unchecked")
	protected CardBinRegState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static CardBinRegState valueOf(String value) {
		CardBinRegState type = (CardBinRegState) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("流程状态错误！");
		}

		return type;
	}
	
	public static List getAll(){
		return getOrderedList(CardBinRegState.ALL);
	}
}
