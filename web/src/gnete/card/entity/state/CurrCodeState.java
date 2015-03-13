package gnete.card.entity.state;

import java.util.HashMap;
import java.util.Map;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

/**
 * @File: CardBINState.java
 *
 * @description:货币代码的状态
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-zwi
 * @version: 1.0
 * @since 1.0 2010-7-27
 */
public class CurrCodeState extends AbstractState {
	public static final Map ALL = new HashMap();

	public static final CurrCodeState NORMAL = new CurrCodeState("正常", "00");	
	public static final CurrCodeState CANCEL = new CurrCodeState("注销", "10");	
	
	@SuppressWarnings("unchecked")
	protected CurrCodeState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static CurrCodeState valueOf(String value) {
		CurrCodeState type = (CurrCodeState) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("流程状态错误！");
		}

		return type;
	}
}
