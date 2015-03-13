package gnete.card.entity.type;

import java.util.LinkedHashMap;
import java.util.Map;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

/**
 * @File: DepositType.java
 *
 * @description: 充值类型
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: aps-bey
 * @version: 1.0
 * @since 1.0 2010-9-17
 */
public class DepositType extends AbstractState {
	public static final Map ALL = new LinkedHashMap();

	public static final DepositType AMT = new DepositType("充值资金子账户充值", "0");
	public static final DepositType REBATE = new DepositType("返利资金子账户充值 ", "2");	
	public static final DepositType TIMES = new DepositType("次卡子账户充值 ", "1");	
	
	@SuppressWarnings("unchecked")
	protected DepositType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static DepositType valueOf(String value) {
		DepositType type = (DepositType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("类型错误！");
		}

		return type;
	}
}
