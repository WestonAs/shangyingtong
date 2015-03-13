package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * @File: ReportType.java
 *
 * @description: 商户划付报表类型
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: aps-lib
 * @version: 1.0
 * @since 1.0 2011-6-20
 */
public class RmaType extends AbstractType{

	public static final Map ALL = new HashMap();
	
	public static final RmaType MERCH_TRANS_RMA = new RmaType("发卡机构商户划付报表", "merchTransRma");
	public static final RmaType MERCH_TRANS_RMA_SET = new RmaType("商户交易资金清算报表", "merchTransRmaSet");

	protected RmaType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static RmaType valueOf(String value) {
		RmaType type = (RmaType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的类型");
		}

		return type;
	}
	
	public static List getAll() {
		return getOrderedList(RmaType.ALL);
	}

}
