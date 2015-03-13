package gnete.card.entity.state;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

/**
 * 商户状态
 * 
 * @author aps-lih
 */
public class MerchState extends AbstractState {
	public static final Map ALL = new HashMap();

	public static final MerchState NORMAL = new MerchState("正常", "00");
	public static final MerchState CANCEL = new MerchState("注销", "10");
	public static final MerchState WAITED = new MerchState("待审核", "20");
	public static final MerchState UNPASS = new MerchState("审核不通过", "30");
	public static final MerchState PRESUB = new MerchState("待提交", "40");
	
	@SuppressWarnings("unchecked")
	protected MerchState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static MerchState valueOf(String value) {
		MerchState type = (MerchState) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("商户状态错误！");
		}

		return type;
	}
	
	public static List getAllOrderByName() {
		return getValueOrderedList(MerchState.ALL);
	}
	
	public static List getWithOutCheck() {
		Map map = new HashMap();
		
		map.put(NORMAL.getValue(), NORMAL);
		map.put(CANCEL.getValue(), CANCEL);
		return getValueOrderedList(map);
	}
	
	public static List getWithCheck() {
		Map map = new HashMap();
		
		map.put(NORMAL.getValue(), NORMAL);
		map.put(WAITED.getValue(), WAITED);
		map.put(UNPASS.getValue(), UNPASS);
		
		return getValueOrderedList(map);
	}
}
