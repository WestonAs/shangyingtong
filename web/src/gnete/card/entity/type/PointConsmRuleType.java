package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/* 11-使用积分兑换赠券， 满积分参数起兑，积分子类型见积分子类型参数，
 * 赠券子类型见赠券子类型参数，兑换率见倍率参数 */

public class PointConsmRuleType extends AbstractType{
	
	public static final Map ALL = new HashMap();
	public static final PointConsmRuleType FULLEXCHANGE = new PointConsmRuleType("满积分起兑", "11");
	
	protected PointConsmRuleType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static PointConsmRuleType valueOf(String value) {
		PointConsmRuleType type = (PointConsmRuleType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的类型");
		}
		return type;
	}
	
	public static List getAll(){
		return getOrderedList(PointConsmRuleType.ALL);
	}

}
