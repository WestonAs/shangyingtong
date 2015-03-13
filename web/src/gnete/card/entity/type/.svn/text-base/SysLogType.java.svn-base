package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

public class SysLogType extends AbstractType {
	
public static final Map ALL = new HashMap();
	
	/**
	 * 
		I’-信息
		‘W’-警告
		‘E’-错误
	 * 
	 */
	public static final SysLogType INFO = new SysLogType("信息","I");
	public static final SysLogType WARN = new SysLogType("警告","W");
	public static final SysLogType ERROR = new SysLogType("错误","E");
	
	@SuppressWarnings("unchecked")
	protected SysLogType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static SysLogType valueOf(String value) {
		SysLogType type = (SysLogType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的类型");
		}
		return type;
	}
	
	public static List getAll(){
		return getOrderedList(SysLogType.ALL);
	}

}
