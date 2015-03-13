package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

public class SetModeType extends AbstractType{

	public static final Map ALL = new HashMap();
	
	/**
	 * 1 分润模式 
	 * 2 成本模式
	 */
	
	public static final SetModeType SHARE = new SetModeType("分润模式", "1");
	public static final SetModeType COST = new SetModeType("成本模式", "2");
	
	protected SetModeType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static SetModeType valueOf(String value) {
		SetModeType type = (SetModeType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的类型");
		}
		return type;
	}
	
	public static List getAll(){
		return getOrderedList(SetModeType.ALL);
	}

}
