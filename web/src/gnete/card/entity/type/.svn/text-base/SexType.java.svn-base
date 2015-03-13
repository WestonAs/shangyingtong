package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

public class SexType extends AbstractType{

	public static final Map ALL = new HashMap();
	
	/*
	性别代码:
		0 男、
		1 女、
	*/
	public static final SexType MALE = new SexType("男", "0");
	public static final SexType FEMALE = new SexType("女", "1");
	
	protected SexType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static SexType valueOf(String value) {
		SexType type = (SexType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的类型");
		}
		return type;
	}
	
	public static List getAll(){
		return getOrderedList(SexType.ALL);
	}

}
