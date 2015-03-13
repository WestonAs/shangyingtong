package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * @File: ReversalType.java
 *
 * @description: 冲正类型
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @modify:
 * @version: 1.0
 * @since 1.0 2011-12-22
 */
public class ReversalType extends AbstractType {

	
	public static final Map ALL = new HashMap();
	
	/** 充值冲正 */
	public static final ReversalType DEPOSIT = new ReversalType("充值冲正","01");
	/** 补登冲正 */
	public static final ReversalType FILL_UP = new ReversalType("补登冲正","02");
	
	@SuppressWarnings("unchecked")
	protected ReversalType(String name, String value) {
		
		super(name, value);
		ALL.put(value, this);
	}

	public static ReversalType valueOf(String value) {
		ReversalType cert = (ReversalType) ALL.get(value);
		
		if (cert == null) {
			throw new RuntimeBizException("不正确的类型");
		}
		return cert;
	}
	
	public static List getAll(){
		return getOrderedList(ReversalType.ALL);
	}
}
