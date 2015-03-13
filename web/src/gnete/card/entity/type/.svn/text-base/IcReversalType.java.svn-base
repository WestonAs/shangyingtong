package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * @File: IcReversalType.java
 *
 * @description: IC卡冲正类型
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @modify:
 * @version: 1.0
 * @since 1.0 2012-9-24 下午02:50:38
 */
public class IcReversalType extends AbstractType {

	
	public static final Map ALL = new HashMap();
	
	/** 换卡冲正 */
	public static final IcReversalType SWAP_CARD = new IcReversalType("换卡冲正","01");
	/** 退卡冲正 */
	public static final IcReversalType CANCEL_CARD = new IcReversalType("退卡冲正","02");
	
	@SuppressWarnings("unchecked")
	protected IcReversalType(String name, String value) {
		
		super(name, value);
		ALL.put(value, this);
	}

	public static IcReversalType valueOf(String value) {
		IcReversalType type = (IcReversalType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的类型");
		}
		return type;
	}
	
	public static List getAll(){
		return getOrderedList(IcReversalType.ALL);
	}
}
