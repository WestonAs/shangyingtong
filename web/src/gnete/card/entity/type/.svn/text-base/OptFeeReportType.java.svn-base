package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * @File: OptFeeReportType.java
 *
 * @description: 运营手续费报表类型
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2010-12-2
 */
public class OptFeeReportType extends AbstractType{
	
	public static final Map ALL = new HashMap();
	
	/*
	 	0 - 运营手续费及分润明细报表
		1 - 发卡机构运营手续费报表
		2 - 管理方分润报表（按分支机构统计）
		3 - 发展方分润报表（按运营代理机构统计）
		4 - 机具出机方分润报表（按出机方统计）
		5 - 机具维护方分润报表（按维护方统计）
	*/
	public static final OptFeeReportType DTAIL = new OptFeeReportType("运营手续费及分润明细报表", "0");
	public static final OptFeeReportType CARD = new OptFeeReportType("发卡机构运营手续费报表", "1");
	public static final OptFeeReportType FENZHI = new OptFeeReportType("管理方分润报表", "2");
	public static final OptFeeReportType PROXY = new OptFeeReportType("发展方分润报表", "3");
	public static final OptFeeReportType PROV = new OptFeeReportType("机具出机方分润报表", "4");
	public static final OptFeeReportType MANAGE = new OptFeeReportType("机具维护方分润报表", "5");
	
	@SuppressWarnings("unchecked")
	protected OptFeeReportType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static OptFeeReportType valueOf(String value) {
		OptFeeReportType type = (OptFeeReportType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的运营手续费报表类型");
		}
		return type;
	}
	
	public static List getAll(){
		return getOrderedList(OptFeeReportType.ALL);
	}
	
}
