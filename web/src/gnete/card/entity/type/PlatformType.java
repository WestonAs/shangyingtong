package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

public class PlatformType extends AbstractType{
	
	public static final Map ALL = new HashMap();
	
	/*
	发起平台类型代码:
		00 终端、
		01 web、
		02 WebService
		03 关联平台
	*/
	public static final PlatformType TERMINAL = new PlatformType("终端", "00");
	public static final PlatformType WEB = new PlatformType("web", "01");
	public static final PlatformType WEBSERVICE = new PlatformType("WebService", "02");
	public static final PlatformType UNION = new PlatformType("关联平台", "03");

	protected PlatformType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static PlatformType valueOf(String value) {
		PlatformType type = (PlatformType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的类型");
		}
		return type;
	}
	
	public static List getAll(){
		return getOrderedList(PlatformType.ALL);
	}
}
