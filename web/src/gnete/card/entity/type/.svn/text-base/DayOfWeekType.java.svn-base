package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

public class DayOfWeekType extends AbstractType{

	public static final Map ALL = new HashMap();
	
	public static final DayOfWeekType SUN = new DayOfWeekType("周日","1");
	public static final DayOfWeekType MON = new DayOfWeekType("周一","2");
	public static final DayOfWeekType TUES = new DayOfWeekType("周二","3");
	public static final DayOfWeekType WED = new DayOfWeekType("周三","4");
	public static final DayOfWeekType THUR = new DayOfWeekType("周四","5");
	public static final DayOfWeekType FRI = new DayOfWeekType("周五","6");
	public static final DayOfWeekType SAT = new DayOfWeekType("周六","7");
	
	protected DayOfWeekType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static DayOfWeekType valueOf(String value) {
		DayOfWeekType type = (DayOfWeekType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的类型");
		}
		return type;
	}
	
	public static List getAll(){
		return getValueOrderedList(DayOfWeekType.ALL);
	}

}
