package gnete.card.entity.state;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

/**
 * @File: CardFreeNumState.java
 *
 * @description: 发卡机构赠送卡数量状态
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @modify:
 * @version: 1.0
 * @since 1.0 2012-8-30 下午04:23:49
 */
public class CardFreeNumState extends AbstractState {
	public static final Map ALL = new HashMap();

	public static final CardFreeNumState UN_SEND = new CardFreeNumState("未送完", "00");
	public static final CardFreeNumState IS_SEND = new CardFreeNumState("已送完", "01");	
	
	@SuppressWarnings("unchecked")
	protected CardFreeNumState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static CardFreeNumState valueOf(String value) {
		CardFreeNumState type = (CardFreeNumState) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("状态错误！");
		}

		return type;
	}
	
	public static List getAll(){
		return getValueOrderedList(CardFreeNumState.ALL);
	}

}
