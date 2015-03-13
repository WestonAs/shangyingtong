package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/***
 * WashTherInvalIdType
 * 当月不洗是否作废
 * @author slt02
 *
 */
public class WashTherInvalIdType extends AbstractType{
	
	public static final Map ALL = new HashMap();
	
	/*
	 	0-不作废
		1-作废
	*/
	public static final WashTherInvalIdType YES = new WashTherInvalIdType("作废", "1");
	public static final WashTherInvalIdType NO = new WashTherInvalIdType("不作废", "0");
	
	@SuppressWarnings("unchecked")
	protected WashTherInvalIdType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static WashTherInvalIdType valueOf(String value) {
		WashTherInvalIdType type = (WashTherInvalIdType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的卡类型");
		}
		return type;
	}
	
	public static List getAll(){
		return getOrderedList(WashTherInvalIdType.ALL);
	}
	
}
