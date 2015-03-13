package gnete.card.entity.type.report;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * @File: ReportType.java
 *
 * @description: 旧报表类型
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2011-6-25
 */
public class OldReportType extends AbstractType {
	public static final Map ALL = new HashMap();

	
	public static final OldReportType EXPIRE_CARD = new OldReportType("过期卡商户明细报表", "expireCard");
	public static final OldReportType ACTIVE_CARD = new OldReportType("卡激活报表", "activeCard");
	public static final OldReportType MERCH_DETAIL = new OldReportType("商户明细报表", "merchDetail");
	public static final OldReportType MERCH_SETT = new OldReportType("商户对账报表", "merchSett");

	@SuppressWarnings("unchecked")
	protected OldReportType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static OldReportType valueOf(String value) {
		OldReportType type = (OldReportType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的类型");
		}

		return type;
	}
	
	public static List getAll() {
		return getOrderedList(OldReportType.ALL);
	}
}
