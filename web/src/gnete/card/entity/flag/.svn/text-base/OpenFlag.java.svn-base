package gnete.card.entity.flag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * @File: OpenFlag.java
 *
 * @author: aps-lih
 * @since 1.0 2010-9-10
 */
public class OpenFlag extends AbstractType {
	public static final Map ALL = new HashMap();

	/**
	  0 未开通  1开通  3注销
	 */
	public static final OpenFlag CLOSED = new OpenFlag("未开通", "0");
	public static final OpenFlag OPEN = new OpenFlag("开通", "1");
	public static final OpenFlag CANCELED = new OpenFlag("注销", "3");
	
	@SuppressWarnings("unchecked")
	protected OpenFlag(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static OpenFlag valueOf(String value) {
		OpenFlag type = (OpenFlag) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的代码");
		}
		return type;
	}
	
	public static List getAll(){
		return getOrderedList(OpenFlag.ALL);
	}
	
	public static List getForCRUD(){
		Map params = new HashMap();
		params.put(CLOSED.getValue(), CLOSED);
		params.put(OPEN.getValue(), OPEN);
		return getOrderedList(params);
	}
}
