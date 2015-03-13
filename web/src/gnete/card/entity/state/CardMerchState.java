package gnete.card.entity.state;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

/**
 * 发卡机构商户关系状态
 * 
 * @author aps-lih
 */
public class CardMerchState extends AbstractState {
	public static final Map ALL = new HashMap();

	public static final CardMerchState NORMAL = new CardMerchState("正常", "00");
	public static final CardMerchState CANCEL = new CardMerchState("注销", "10");	
	
	@SuppressWarnings("unchecked")
	protected CardMerchState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static CardMerchState valueOf(String value) {
		CardMerchState type = (CardMerchState) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("发卡机构商户关系状态错误！");
		}

		return type;
	}
	
	public static List getAll() {
		return getValueOrderedList(CardMerchState.ALL);
	}
}
