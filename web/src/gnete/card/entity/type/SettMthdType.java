package gnete.card.entity.type;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SettMthdType extends AbstractType{
	
	public static final Map ALL = new HashMap();
	
	/*
	清算方法类型代码:
		0 不清算、
		1 按固定金额清算、
		2 按固定倍率清算、
	*/
	
	public static final SettMthdType NOSETTLE = new SettMthdType("不清算", "0");
	public static final SettMthdType FIXSUM = new SettMthdType("按固定金额清算", "1");
	public static final SettMthdType FIXRATION = new SettMthdType("按固定倍率清算", "2");
	
	@SuppressWarnings("unchecked")
	protected SettMthdType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static SettMthdType valueOf(String value) {
		SettMthdType type = (SettMthdType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的方法");
		}
		return type;
	}
	
	public static List getAll(){
		return getOrderedList(SettMthdType.ALL);
	}
	
	public static List getForAccu(){
		Map params = new HashMap();
		params.put(NOSETTLE.getValue(), NOSETTLE);
		params.put(FIXSUM.getValue(), FIXSUM);
		return getOrderedList(params);
	}
}
