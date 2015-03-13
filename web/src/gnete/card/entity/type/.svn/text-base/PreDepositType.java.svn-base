package gnete.card.entity.type;

import java.util.HashMap;
import java.util.Map;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

/**
 * @File: PresellState.java
 *
 * @description: 预充标识
 *
 * @author: aps-lih
 * @version: 1.0
 * @since 1.0 2010-11-24
 */
public class PreDepositType extends AbstractState {
	public static final Map ALL = new HashMap();

	public static final PreDepositType REAL_DEPOSIT = new PreDepositType("实时充值", "0");
	public static final PreDepositType PRE_DEPOSIT = new PreDepositType("预充值", "1");	
	
	@SuppressWarnings("unchecked")
	protected PreDepositType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static PreDepositType valueOf(String value) {
		PreDepositType type = (PreDepositType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("流程状态错误！");
		}

		return type;
	}
}
