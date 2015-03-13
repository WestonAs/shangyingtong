package gnete.card.entity.type.report;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * @File: SingleProductReportType.java
 *
 * @description: 单机产品报表类型
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @modify:
 * @version: 1.0
 * @since 1.0 2012-7-25 下午02:17:59
 */
public class SingleProductReportType extends AbstractType{
	
	public static final Map ALL = new HashMap();
	
	/*
	 	0-套餐POS缴费清算划账日报表（套餐费）
		1-套餐POS缴费清算划账日报表（制卡费）
	*/
	public static final SingleProductReportType SINGLE_PLAN_FEE = new SingleProductReportType("套餐POS缴费清算划账日报表（套餐费）", "0");
	public static final SingleProductReportType SINGLE_MAKE_FEE = new SingleProductReportType("套餐POS缴费清算划账日报表（制卡费）", "1");
	
	@SuppressWarnings("unchecked")
	protected SingleProductReportType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static SingleProductReportType valueOf(String value) {
		SingleProductReportType type = (SingleProductReportType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的卡类型");
		}
		return type;
	}
	
	public static List getAll(){
		return getValueOrderedList(SingleProductReportType.ALL);
	}
	
}
