package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

public class ExpirMthdType extends AbstractType{
	
	public static final Map ALL = new HashMap();
	
	/**
	 * 1：指定失效日期，到该日期失效
	 * 2：指定有效期（月数），从售卡日起，经过该有效期月数失效
	*/
	public static final ExpirMthdType EXPIR_DATE = new ExpirMthdType("指定失效日期", "1");
	public static final ExpirMthdType EXPIR_MONTH = new ExpirMthdType("指定有效月数", "2");

	protected ExpirMthdType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static ExpirMthdType valueOf(String value) {
		ExpirMthdType type = (ExpirMthdType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的类型");
		}
		return type;
	}
	
	public static List getAll(){
		return getOrderedList(ExpirMthdType.ALL);
	}
	
}
