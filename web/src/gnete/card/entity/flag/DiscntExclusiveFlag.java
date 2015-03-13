package gnete.card.entity.flag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * @File: DiscntExclusiveFlag.java
 *
 * @description: 折扣排他标志
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-zwi
 * @version: 1.0
 * @since 1.0 2010-10-10
 */
public class DiscntExclusiveFlag extends AbstractType {
	public static final Map ALL = new HashMap();

	/**
	  0-允许与其它折扣叠加（折上折）
	  1-不允许与其他折扣叠加
	 */
	public static final DiscntExclusiveFlag ALLOW_OTHER = new DiscntExclusiveFlag("允许与其它折扣叠加（折上折）", "0");
	public static final DiscntExclusiveFlag NOT_ALLOW = new DiscntExclusiveFlag("不允许与其他折扣叠加", "1");
	
	@SuppressWarnings("unchecked")
	protected DiscntExclusiveFlag(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static DiscntExclusiveFlag valueOf(String value) {
		DiscntExclusiveFlag type = (DiscntExclusiveFlag) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的标志");
		}
		return type;
	}
	
	public static List getAll(){
		return getOrderedList(DiscntExclusiveFlag.ALL);
	}
	
	public static List getNotAllowList() {
		Map params = new HashMap();
		params.put(NOT_ALLOW.getValue(), NOT_ALLOW);
		return getOrderedList(params);
	}
}
