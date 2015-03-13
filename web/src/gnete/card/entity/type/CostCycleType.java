package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * @author aps-mjn
 */
public class CostCycleType extends AbstractType {
	public static final Map ALL = new HashMap();
	
	/*
	手续费计算周期
	*/
	public static final CostCycleType DAY = new CostCycleType("按天", "1");
	public static final CostCycleType MONTH = new CostCycleType("按月", "2");
	public static final CostCycleType SEASON = new CostCycleType("按季", "3");
	public static final CostCycleType YEAR = new CostCycleType("按年", "4");
	
	
	public static CostCycleType valueOf(String value) {
		CostCycleType type = (CostCycleType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("没有该计费周期");
		}

		return type;
	}
	
	@SuppressWarnings("unchecked")
	protected CostCycleType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static List getList() {
		return getOrderedList(ALL);
	}
	
	public static List getDay(){
		Map params = new HashMap();
		params.put(DAY.getValue(), DAY);
		return getOrderedList(params);
	}
	
	public static List getMonth(){
		Map params = new HashMap();
		params.put(MONTH.getValue(), MONTH);
		
		return getOrderedList(params);
	}
	
	public static List getYear(){
		Map params = new HashMap();
		params.put(YEAR.getValue(), YEAR);
		
		return getOrderedList(params);
	}
	
	public static List getDayMonth(){
		Map params = new HashMap();
		params.put(DAY.getValue(), DAY);
		params.put(MONTH.getValue(), MONTH);
		
		return getOrderedList(params);
	}
	
}
