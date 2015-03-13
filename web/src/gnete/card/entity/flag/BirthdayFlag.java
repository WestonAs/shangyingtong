package gnete.card.entity.flag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * @File: BirthdayFlag.java
 *
 * @description: 生日标志
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-zwi
 * @version: 1.0
 * @since 1.0 2010-9-29
 */
public class BirthdayFlag extends AbstractType {
	public static final Map ALL = new HashMap();

	/**
	  0 所有日期都有效 
	  1 仅生日有效
	 */
	public static final BirthdayFlag ALL_DATE = new BirthdayFlag("所有日期都有效", "0");
	public static final BirthdayFlag BIRTHDAY = new BirthdayFlag("仅生日有效", "1");
	
	@SuppressWarnings("unchecked")
	protected BirthdayFlag(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static BirthdayFlag valueOf(String value) {
		BirthdayFlag type = (BirthdayFlag) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的标志");
		}
		return type;
	}
	
	public static List getAll(){
		return getValueOrderedList(BirthdayFlag.ALL);
	}
}
