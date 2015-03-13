package gnete.card.entity.type.report;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * @File: ManualReportType.java
 *
 * @description: 手动生成报表类型
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2011-6-23
 */
public class ManualReportType extends AbstractType {
	public static final Map ALL = new HashMap();

	
	public static final ManualReportType DAILY = new ManualReportType("日报表", "0");
	public static final ManualReportType MONTH = new ManualReportType("月报表", "1");

	@SuppressWarnings("unchecked")
	protected ManualReportType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static ManualReportType valueOf(String value) {
		ManualReportType type = (ManualReportType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的类型");
		}

		return type;
	}
	
	public static List getAll() {
		return getOrderedList(ManualReportType.ALL);
	}
}
