package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

public class CommType extends AbstractType {
	
public static final Map ALL = new HashMap();
	
	/**
	 * 通信方式
	 * 0-有线拨号 1-GRPS 2-CDMA 3-MIS 4-虚拟终端
	 * 
	 */
	public static final CommType DIAL = new CommType("有线拨号","0");
	public static final CommType GRPS = new CommType("GRPS","1");
	public static final CommType CDMA = new CommType("CDMA","2");
	public static final CommType MIS = new CommType("MIS","3");
	public static final CommType VIRTUAL  = new CommType("虚拟终端","4");
	
	@SuppressWarnings("unchecked")
	protected CommType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static CommType valueOf(String value) {
		CommType type = (CommType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的类型");
		}
		return type;
	}
	
	public static List getAll(){
		return getOrderedList(CommType.ALL);
	}

}
