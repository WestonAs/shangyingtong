package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

/**
 * @File: xferType.java
 *
 * @description: 划账方式
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: aps-lib
 * @version: 1.0
 * @since 1.0 2011-2-22
 */

public class XferType extends AbstractState{
	
	public static final Map ALL = new HashMap();

	public static final XferType MONTH_REMIT = new XferType("指定月日期划账", "01");
	public static final XferType WEEK_REMIT = new XferType("指定周日期划账 ", "02");	
	public static final XferType LIMIT_REMIT = new XferType("满额度划账", "03");
	public static final XferType MONTH_LIIMT_REMIT = new XferType("指定月日期或满额度划账", "04");	
	public static final XferType WEEK_LIMIT_REMIT = new XferType("指定周日期或满额度划账", "05");	

	protected XferType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static XferType valueOf(String value) {
		XferType type = (XferType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("流程状态错误！");
		}

		return type;
	}

	public static List getAll(){
		return getOrderedList(XferType.ALL);
	}

}
