package gnete.card.entity.flag;

import java.util.HashMap;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * @File: OKFlag.java
 *
 * @description: 定版标志
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-zwi
 * @version: 1.0
 * @since 1.0 2010-8-5
 */
public class OKFlag extends AbstractType {
	public static final Map ALL = new HashMap();

	public static final OKFlag FALSE = new OKFlag("未定版", "0");
	public static final OKFlag TRUE = new OKFlag("定版", "1");
	
	@SuppressWarnings("unchecked")
	protected OKFlag(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static OKFlag valueOf(String value) {
		OKFlag type = (OKFlag) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的代码");
		}
		return type;
	}
}
