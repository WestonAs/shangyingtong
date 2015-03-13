package gnete.card.entity.flag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * @File: CheckIpFlag.java
 *
 * @author: aps-lih
 */
public class CheckIpFlag extends AbstractType {
	public static final Map ALL = new HashMap();

	public static final CheckIpFlag NO = new CheckIpFlag("不验证", "0");
	public static final CheckIpFlag YES = new CheckIpFlag("验证", "1");
	
	@SuppressWarnings("unchecked")
	protected CheckIpFlag(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static CheckIpFlag valueOf(String value) {
		CheckIpFlag type = (CheckIpFlag) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的代码");
		}
		return type;
	}
	
	public static List getAll(){
		return getOrderedList(CheckIpFlag.ALL);
	}
}
