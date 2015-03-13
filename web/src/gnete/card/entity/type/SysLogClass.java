package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

public class SysLogClass extends AbstractType {
	
public static final Map ALL = new HashMap();
	
	/**
	 * 
	 * S：系统管理员[程序/数据库错误]，U：业务人员[数据错]
	 * 
	 */
	public static final SysLogClass INFO = new SysLogClass("系统管理员[程序/数据库错误]","S");
	public static final SysLogClass WARN = new SysLogClass("业务人员[数据错]","U");
	
	@SuppressWarnings("unchecked")
	protected SysLogClass(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static SysLogClass valueOf(String value) {
		SysLogClass type = (SysLogClass) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的类型");
		}
		return type;
	}
	
	public static List getAll(){
		return getOrderedList(SysLogClass.ALL);
	}

}
