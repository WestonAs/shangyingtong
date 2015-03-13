package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * 计费周期类型
 * @author aps-lib
 */
public class FeeCycleType extends AbstractType {
	public static final Map ALL = new HashMap();
	
	/*
	1: 按自然日
	2: 按自然月
	3: 按自然季度
	4: 按自然年
	5: 按自然半年
	6: 协议月
	9: 自定义
	*/

	public static final FeeCycleType NATURE_DAY = new FeeCycleType("按自然日", "1");
	public static final FeeCycleType NATURE_MONTH = new FeeCycleType("按自然月", "2");
	public static final FeeCycleType NATURE_SEASON = new FeeCycleType("按自然季度", "3");
	public static final FeeCycleType NATURE_YEAR = new FeeCycleType("按自然年", "4");
	public static final FeeCycleType NATURE_HALF_YEAR = new FeeCycleType("按自然半年", "5");
	public static final FeeCycleType PROTOCOL_MONTH = new FeeCycleType("协议月", "6");
	
	
	@SuppressWarnings("unchecked")
	protected FeeCycleType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static FeeCycleType valueOf(String value) {
		FeeCycleType type = (FeeCycleType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("没有该计费周期类型。");
		}

		return type;
	}
	
	public static List getAll(){
		return getValueOrderedList(FeeCycleType.ALL);
	}
	
	//调整周期类型
	public static List getAdjustCycleType() {
		Map params = new HashMap();
		params.put(NATURE_MONTH.getValue(), NATURE_MONTH);
		params.put(NATURE_SEASON.getValue(), NATURE_SEASON);
		params.put(NATURE_YEAR.getValue(), NATURE_YEAR);
		params.put(NATURE_HALF_YEAR.getValue(), NATURE_HALF_YEAR);
		params.put(PROTOCOL_MONTH.getValue(), PROTOCOL_MONTH);
		
		return getValueOrderedList(params);
	}

}
