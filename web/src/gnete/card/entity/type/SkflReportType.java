package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * 
  * @Project: Card
  * @File: SkflReportType.java
  * @See:
  * @description：售卡代理月报表类型
  * @author: aps-zbw
  * @modified:
  * @Email: aps-zbw@cnaps.com.cn
  * @Date: 2010-12-4
  * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2010 版权所有
  * @version:  V1.0
 */
public class SkflReportType extends AbstractType{
	
	public static final Map ALL = new HashMap();
	
	public static final SkflReportType CARD = new SkflReportType("发卡机构售卡返利报表", "0");
	public static final SkflReportType SELL = new SkflReportType("售卡代理售卡返利报表", "1");
	//自定义类型用于发卡机构选择售卡代理
	public static final SkflReportType CARD_SELL = new SkflReportType("发卡机构售卡返利报表", "2"); 
	//自定义类型用于售卡代理选择发卡机构
	public static final SkflReportType SELL_CARD = new SkflReportType("售卡代理售卡返利报表", "3");

	protected SkflReportType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static SkflReportType valueOf(String value) {
		SkflReportType type = (SkflReportType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的售卡代理报表类型");
		}
		return type;
	}
	
	public static List getAll(){
		return getOrderedList(SkflReportType.ALL);
	}
	
	
	
}
