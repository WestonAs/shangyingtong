package gnete.card.entity.state;

import java.util.HashMap;
import java.util.Map;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

/**
 * 签单卡账单状态
 *	00 初始   
 *	10 已还款  
 *	11 部分还款  
 * @author aps-mjn
 */
public class SignCardAccListState extends AbstractState {
	public static final Map ALL = new HashMap();

	public static final SignCardAccListState INITIAL = new SignCardAccListState("初始", "00");
	public static final SignCardAccListState WHOLE = new SignCardAccListState("已还款", "10");	
	public static final SignCardAccListState PART = new SignCardAccListState("部分还款", "11");	
	
	@SuppressWarnings("unchecked")
	protected SignCardAccListState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static SignCardAccListState valueOf(String value) {
		SignCardAccListState type = (SignCardAccListState) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("流程状态错误！");
		}

		return type;
	}
}
