package gnete.card.entity.type;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @File: FeeType.java
 *
 * @description: 手续费收取方式，0 商户组收费 1 各商户收费
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2010-11-3
 */
public class FeeType extends AbstractType{
	public static final Map ALL = new HashMap();
	
	/*
	手续费收取方式:
		0  商户组收费   1 各商户收费
	*/
	public static final FeeType GROUP = new FeeType("商户组收费", "0");
	public static final FeeType MERCHANT = new FeeType("各商户收费", "1");
	
	@SuppressWarnings("unchecked")
	protected FeeType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static FeeType valueOf(String value) {
		FeeType type = (FeeType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的类型");
		}
		return type;
	}
	
	public static List getAll(){
		return getOrderedList(FeeType.ALL);
	}
}
