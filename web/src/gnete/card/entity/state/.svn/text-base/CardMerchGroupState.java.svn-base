package gnete.card.entity.state;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

/**
 * @File: CardMerchGroupState.java
 *
 * @description: 发卡机构商户组状态
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2010-11-3
 */
public class CardMerchGroupState extends AbstractState {
	public static final Map ALL = new HashMap();

	public static final CardMerchGroupState NORMAL = new CardMerchGroupState("正常", "00");
	public static final CardMerchGroupState CANCEL = new CardMerchGroupState("注销", "10");	
	
	@SuppressWarnings("unchecked")
	protected CardMerchGroupState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static CardMerchGroupState valueOf(String value) {
		CardMerchGroupState type = (CardMerchGroupState) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("发卡机构商户组关系状态错误！");
		}

		return type;
	}
	
	public static List getAll(){
		return getOrderedList(CardMerchGroupState.ALL);
	}
}
