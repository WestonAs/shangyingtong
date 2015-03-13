package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * @File: CostRecordType.java
 *
 * @description: 单机产品费用记录类型
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @modify:
 * @version: 1.0
 * @since 1.0 2012-2-9
 */
public class CostRecordType extends AbstractType {

	
	public static final Map ALL = new HashMap();
	
	public static final CostRecordType PLAN_AMT = new CostRecordType("套餐费用","0");
	public static final CostRecordType MAKE_AMT = new CostRecordType("制卡费用","1");
	
	@SuppressWarnings("unchecked")
	protected CostRecordType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static CostRecordType valueOf(String value) {
		CostRecordType cert = (CostRecordType) ALL.get(value);
		
		if (cert == null) {
			throw new RuntimeBizException("不正确的类型");
		}
		return cert;
	}
	
	public static List getList(){
		return getOrderedList(CostRecordType.ALL);
	}
}
