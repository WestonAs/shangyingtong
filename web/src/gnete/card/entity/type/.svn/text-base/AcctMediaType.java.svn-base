package gnete.card.entity.type;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @File: AcctMediaType.java
 *
 * @description: 账户介质类型
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @modify:
 * @version: 1.0
 * @since 1.0 2012-8-21 上午11:43:57
 */
public class AcctMediaType extends AbstractType{
	public static final Map ALL = new HashMap();
	
	/** 0 银行卡 */
	public static final AcctMediaType BANK_CARD = new AcctMediaType("银行卡", "0");
	/** 1 存折 */
	public static final AcctMediaType BANK_BOOK = new AcctMediaType("存折", "1");
	
	@SuppressWarnings("unchecked")
	protected AcctMediaType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static AcctMediaType valueOf(String value) {
		AcctMediaType type = (AcctMediaType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的类型");
		}
		return type;
	}
	
	public static List getAll(){
		return getValueOrderedList(AcctMediaType.ALL);
	}
	
}
