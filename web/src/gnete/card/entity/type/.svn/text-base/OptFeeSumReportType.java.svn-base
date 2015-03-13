package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * @File: OptFeeSumReportType.java
 *
 * @description: 运营手续费及分润汇总报表类型
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2010-12-27
 */
public class OptFeeSumReportType extends AbstractType{
	
	public static final Map ALL = new HashMap();
	
	/*
		1 - 发卡机构运营手续费汇总报表
		2 - 管理方分润汇总报表（按分支机构统计）
		3 - 发展方分润汇总报表（按运营代理机构统计）
		4 - 机具出机方分润汇总报表（按出机方统计）
		5 - 机具维护方分润汇总报表（按维护方统计）
	*/
	public static final OptFeeSumReportType CARD = new OptFeeSumReportType("发卡机构运营手续费汇总报表", "1");
	public static final OptFeeSumReportType FENZHI = new OptFeeSumReportType("管理方分润汇总报表", "2");
	public static final OptFeeSumReportType PROXY = new OptFeeSumReportType("发展方分润汇总报表", "3");
	public static final OptFeeSumReportType PROV = new OptFeeSumReportType("机具出机方分润汇总报表", "4");
	public static final OptFeeSumReportType MANAGE = new OptFeeSumReportType("机具维护方分润汇总报表", "5");
	
	@SuppressWarnings("unchecked")
	protected OptFeeSumReportType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static OptFeeSumReportType valueOf(String value) {
		OptFeeSumReportType type = (OptFeeSumReportType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的运营手续费汇总报表类型");
		}
		return type;
	}
	
	public static List getAll(){
		return getOrderedList(OptFeeSumReportType.ALL);
	}
	
}
