package gnete.card.entity.flag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * @File: ShowFlag.java
 *
 * @description: 显示标志
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2010-11-18
 */
public class ShowFlag extends AbstractType {
	public static final Map ALL = new HashMap();

	public static final ShowFlag HIDE = new ShowFlag("不显示", "0");
	public static final ShowFlag SHOW = new ShowFlag("显示", "1");
	
	@SuppressWarnings("unchecked")
	protected ShowFlag(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static ShowFlag valueOf(String value) {
		ShowFlag type = (ShowFlag) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的代码");
		}
		return type;
	}
	
	public static List getAll(){
		return getOrderedList(ShowFlag.ALL);
	}
	
}
