package gnete.card.entity.state;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

/**
 * 购卡客户状态
 * 
 * @author aps-bey
 */
public class CardCustomerState extends AbstractState {
	public static final Map ALL = new HashMap();

	public static final CardCustomerState NORMAL = new CardCustomerState("正常", "00");
	public static final CardCustomerState DISABLE = new CardCustomerState("失效", "01");	
	public static final CardCustomerState USED = new CardCustomerState("已启用", "03");	
	
	@SuppressWarnings("unchecked")
	protected CardCustomerState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static CardCustomerState valueOf(String value) {
		CardCustomerState type = (CardCustomerState) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("状态错误！");
		}

		return type;
	}
	
	public static List getAll(){
		return getOrderedList(CardCustomerState.ALL);
	}
}
