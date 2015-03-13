package gnete.card.entity.type.report;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * @File: FenzhiFeeShareType.java
 *
 * @description: 运营分支机构角色查看的运营手续费及分润报表类型
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2011-6-10
 */
public class FenzhiFeeShareType extends AbstractType{
	
	public static final Map ALL = new HashMap();
	
	/*
	 	0-积分卡业务运营手续费收入汇总报表
		1-积分卡业务运营手续费收入明细报表
		2-积分卡业务运营手续费成本汇总报表
		3-积分卡业务运营手续费成本明细报表
	*/
	public static final FenzhiFeeShareType INCOME_SUMMARY = new FenzhiFeeShareType("发卡机构运营手续费收入汇总报表", "0");
	public static final FenzhiFeeShareType INCOME_DETAIL = new FenzhiFeeShareType("发卡机构运营手续费收入明细报表", "1");
	public static final FenzhiFeeShareType INCOME_DETAIL_ADJUST = new FenzhiFeeShareType("发卡机构运营手续费收入明细调整报表", "4");
//	public static final FenzhiFeeShareType COST_SUMMARY = new FenzhiFeeShareType("运营手续费成本汇总报表", "2");
//	public static final FenzhiFeeShareType COST_DETAIL = new FenzhiFeeShareType("运营手续费成本明细报表", "3");
	
	@SuppressWarnings("unchecked")
	protected FenzhiFeeShareType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static FenzhiFeeShareType valueOf(String value) {
		FenzhiFeeShareType type = (FenzhiFeeShareType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的卡类型");
		}
		return type;
	}
	
	public static List getAll(){
		return getOrderedList(FenzhiFeeShareType.ALL);
	}
	
}
