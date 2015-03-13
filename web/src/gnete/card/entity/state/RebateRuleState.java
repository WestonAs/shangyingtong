package gnete.card.entity.state;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

/**
 * 返利规则状态
 * 
 * @author aps-bey
 */
public class RebateRuleState extends AbstractState {
	public static final Map ALL = new HashMap();

	public static final RebateRuleState NORMAL = new RebateRuleState("正常", "00");
	public static final RebateRuleState DISABLE = new RebateRuleState("失效", "01");	
	public static final RebateRuleState USED = new RebateRuleState("已启用", "03");	
	
	@SuppressWarnings("unchecked")
	protected RebateRuleState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static RebateRuleState valueOf(String value) {
		RebateRuleState type = (RebateRuleState) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("状态错误！");
		}

		return type;
	}
	
	public static List getAll() {
		return getOrderedList(RebateRuleState.ALL);
	}
}
