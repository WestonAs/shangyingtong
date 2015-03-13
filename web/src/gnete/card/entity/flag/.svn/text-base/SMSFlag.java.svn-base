package gnete.card.entity.flag;

import java.util.HashMap;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * @File: OKFlag.java
 *
 * @description: 短信是否开通标志
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-zwi
 * @version: 1.0
 * @since 1.0 2010-8-5
 */
public class SMSFlag extends AbstractType {
	public static final Map ALL = new HashMap();

	public static final SMSFlag CLOSED = new SMSFlag("未开通", "0");
	public static final SMSFlag OPEN = new SMSFlag("开通", "1");
	
	@SuppressWarnings("unchecked")
	protected SMSFlag(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static SMSFlag valueOf(String value) {
		SMSFlag type = (SMSFlag) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的代码");
		}
		return type;
	}
}
