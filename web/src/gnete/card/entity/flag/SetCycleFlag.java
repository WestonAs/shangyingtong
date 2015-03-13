package gnete.card.entity.flag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * @File: SetCycleFlag.java
 *
 * @author: aps-lih
 * @since 1.0 2010-9-10
 */
public class SetCycleFlag extends AbstractType {
	public static final Map ALL = new HashMap();

	/**
	  0：每天、1：7天、2：15天、3：月，4：年
	 */
	public static final SetCycleFlag DAY = new SetCycleFlag("每天", "0");
	public static final SetCycleFlag WEEK = new SetCycleFlag("7天", "1");
	public static final SetCycleFlag HALF_MONTH = new SetCycleFlag("15天", "2");
	public static final SetCycleFlag MONTH = new SetCycleFlag("月", "3");
	public static final SetCycleFlag YEAR = new SetCycleFlag("年", "4");
	
	@SuppressWarnings("unchecked")
	protected SetCycleFlag(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static SetCycleFlag valueOf(String value) {
		SetCycleFlag type = (SetCycleFlag) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的代码");
		}
		return type;
	}
	
	public static List getAll(){
		return getOrderedList(SetCycleFlag.ALL);
	}
}
