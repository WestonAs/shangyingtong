package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * 返利类型
 * @Project: CardWeb
 * @File: RebateRuleType.java
 * @See:
 * @author: aps-qfg
 * @modified:
 * @Email: aps-qfg@cnaps.com.cn
 * @Date: 2013-4-1上午11:01:57
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2013 版权所有
 * @version: V1.0
 */
public class RebateRuleType extends AbstractType{

	public static final Map ALL = new HashMap();
	
	public static final RebateRuleType YES = new RebateRuleType("通用", "Y");
	public static final RebateRuleType NO = new RebateRuleType("非通用", "N");
	public static final RebateRuleType TOTAL = new RebateRuleType("POS返利", "A");

	protected RebateRuleType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static RebateRuleType valueOf(String value) {
		RebateRuleType type = (RebateRuleType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的类型");
		}

		return type;
	}
	
	public static List getAll() {
		return getOrderedList(RebateRuleType.ALL);
	}
	
	public static List getNotCommon() {
		Map params = new HashMap();
		params.put(NO.getValue(), NO);
		params.put(TOTAL.getValue(), TOTAL);
		return getOrderedList(params);
	}

}
