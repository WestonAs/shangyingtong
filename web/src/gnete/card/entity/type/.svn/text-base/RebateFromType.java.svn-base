package gnete.card.entity.type;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @File: RebateFromType.java
 *
 * @description: 返利来源类型
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @modify:
 * @version: 1.0
 * @since 1.0 2012-6-28 上午10:41:31
 */
public class RebateFromType extends AbstractType{
	public static final Map ALL = new HashMap();
	
	/** 售卡 0 */
	public static final RebateFromType SALE_CARD = new RebateFromType("售卡返利", "0");
	/** 充值 1 */
	public static final RebateFromType DEPOSIT = new RebateFromType("充值返利", "1");
	
	@SuppressWarnings("unchecked")
	protected RebateFromType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static RebateFromType valueOf(String value) {
		RebateFromType type = (RebateFromType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的类型");
		}
		return type;
	}
	
	public static List getAll(){
		return getValueOrderedList(RebateFromType.ALL);
	}
	
}
