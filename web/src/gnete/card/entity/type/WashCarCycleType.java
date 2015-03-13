package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/***
 * WashCarCycleType
 * 洗车周期
 * @author slt02
 *
 */
public class WashCarCycleType extends AbstractType{
	
	public static final Map ALL = new HashMap();
	
	/*
	 	Y-年洗
		M-月洗
	*/
	public static final WashCarCycleType YEAR = new WashCarCycleType("年洗", "Y");
	public static final WashCarCycleType MONTH = new WashCarCycleType("月洗", "M");
	
	@SuppressWarnings("unchecked")
	protected WashCarCycleType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static WashCarCycleType valueOf(String value) {
		WashCarCycleType type = (WashCarCycleType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的卡类型");
		}
		return type;
	}
	
	public static List getAll(){
		return getOrderedList(WashCarCycleType.ALL);
	}
	
}
