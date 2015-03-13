package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * @author aps-bey
 */
public class RebateRuleCalType extends AbstractType {
	public static final Map ALL = new HashMap();

	
	/*
	 * 返利规则计算方式：
		0-分段 
		1-累进
		2-固定金额
	*/	
	public static final RebateRuleCalType SECT	 = new RebateRuleCalType("分段比例", "0");
	public static final RebateRuleCalType SSUM = new RebateRuleCalType("金额段阶梯比例", "1");
	public static final RebateRuleCalType FIXED = new RebateRuleCalType("分段固定金额", "2");
	
	@SuppressWarnings("unchecked")
	protected RebateRuleCalType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static RebateRuleCalType valueOf(String value) {
		RebateRuleCalType type = (RebateRuleCalType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的类型");
		}

		return type;
	}
	
	public static List getAll() {
		return getOrderedList(RebateRuleCalType.ALL);
	}
	
}
