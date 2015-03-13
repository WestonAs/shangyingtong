package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * 
 * @Project: Card
 * @File: ShfrReportType.java
 * @See:
 * @description：<ul>商户代理分润报表</ul>
 * @author: aps-zbw
 * @modified:
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2010-12-5
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2010 版权所有
 * @version: V1.0
 */
public class ShfrReportType extends AbstractType {

	public static final Map ALL = new HashMap();

	public static final ShfrReportType CARD = new ShfrReportType("发卡机构代理分润报表", "0");
	public static final ShfrReportType MERCHANT = new ShfrReportType("商户代理代理分润报表", "1");
	// 自定义类型用于发卡机构选择售卡代理
	public static final ShfrReportType CARD_MERCHANT = new ShfrReportType("发卡机构代理分润报表", "2");
	// 自定义类型用于售卡代理选择发卡机构
	public static final ShfrReportType MERCHANT_CARD = new ShfrReportType("商户代理代理分润报表", "3");
	

	protected ShfrReportType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static ShfrReportType valueOf(String value) {
		ShfrReportType type = (ShfrReportType) ALL.get(value);

		if (type == null) {
			throw new RuntimeBizException("不正确的商户分润报表类型");
		}
		return type;
	}

	public static List getAll() {
		return getOrderedList(SkflReportType.ALL);
	}

}
