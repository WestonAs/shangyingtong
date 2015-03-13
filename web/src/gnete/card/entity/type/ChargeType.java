package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * @File: ChargeType.java
 *
 * @description: 单机产品套餐收费标准
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @modify:
 * @version: 1.0
 * @since 1.0 2012-2-8
 */
public class ChargeType extends AbstractType {

	
	public static final Map ALL = new HashMap();
	
	public static final ChargeType FIXED_AMT = new ChargeType("固定金额","0");
	public static final ChargeType PRECENTED = new ChargeType(" 收费金额百分比","1");
	
	@SuppressWarnings("unchecked")
	protected ChargeType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static ChargeType valueOf(String value) {
		ChargeType cert = (ChargeType) ALL.get(value);
		
		if (cert == null) {
			throw new RuntimeBizException("不正确的类型");
		}
		return cert;
	}
	
	public static List getList(){
		return getOrderedList(ChargeType.ALL);
	}
}
