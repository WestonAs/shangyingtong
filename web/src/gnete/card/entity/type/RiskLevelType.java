package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * @description 风险级别
 */
public class RiskLevelType extends AbstractType{
	
	public static final Map ALL = new HashMap();
	
	/** 0 表示低 */
	public static final RiskLevelType LOW = new RiskLevelType("低", "0");
	/** 1 表示中 */
	public static final RiskLevelType MIDDLE = new RiskLevelType("中", "1");
	/** 2 表示高 */
	public static final RiskLevelType HIGH = new RiskLevelType("高", "2");
	
	
	protected RiskLevelType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static RiskLevelType valueOf(String value) {
		RiskLevelType type = (RiskLevelType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的类型");
		}
		return type;
	}
	
	public static List getAll(){
		return getValueOrderedList(RiskLevelType.ALL);
	}
}
