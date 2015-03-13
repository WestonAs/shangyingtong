package gnete.card.entity.flag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * 代收付标志
 * @author aps-lib
 * since 2011-8-24
 */
public class CpFlag extends AbstractType{

	public static final Map ALL = new HashMap();
	
	public static final CpFlag COLLECT = new CpFlag("代收", "S");
	public static final CpFlag PAY = new CpFlag("代付", "F");
	
	protected CpFlag(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static CpFlag valueOf(String value) {
		CpFlag type = (CpFlag) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的标志");
		}
		return type;
	}
	
	public static List getAll(){
		return getOrderedList(CpFlag.ALL);
	}

}
