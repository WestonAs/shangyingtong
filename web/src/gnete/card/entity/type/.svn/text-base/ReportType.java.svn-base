package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * @File: ReportType.java
 *
 * @description: 下载自动生成报表的报表类型
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2011-3-14
 */
public class ReportType extends AbstractType {
	public static final Map ALL = new HashMap();

	
	public static final ReportType CARD	 = new ReportType("发卡机构报表", "cardBranch");
	public static final ReportType MERCH = new ReportType("商户报表", "merch");
	public static final ReportType PROXY = new ReportType("售卡代理报表", "saleProxy");

	/*
	 * 旧报表类型
	 */
	public static final ReportType EXPIRE_CARD = new ReportType("过期商户明细报表", "expireCard");
	public static final ReportType ACTIVE_CARD = new ReportType("卡激活报表", "activeCard");
	public static final ReportType MERCH_SETT = new ReportType("商户对账文件", "merchSett");
	public static final ReportType MERCH_DETAIL = new ReportType("商户明细报表", "merchDetail");
	
	@SuppressWarnings("unchecked")
	protected ReportType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static ReportType valueOf(String value) {
		ReportType type = (ReportType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的类型");
		}

		return type;
	}
	
	public static List getAll() {
		return getOrderedList(ReportType.ALL);
	}
	
	/**
	 * 得到旧报表类型列表
	 * @return
	 */
	public static List getOldReportTypeList(){
		Map params = new HashMap();
		params.put(EXPIRE_CARD.getValue(), EXPIRE_CARD);
		params.put(ACTIVE_CARD.getValue(), ACTIVE_CARD);
		params.put(MERCH_SETT.getValue(), MERCH_SETT);
		params.put(MERCH_DETAIL.getValue(), MERCH_DETAIL);
		return getOrderedList(params);
	}
}
