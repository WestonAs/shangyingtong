package gnete.card.entity.state;

import java.util.HashMap;
import java.util.Map;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

/**
 * @File: CustomerRebateState.java
 *
 * @description: 客户返利状态
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-bey
 * @version: 1.0
 * @since 1.0 2010-7-27
 */
public class CustomerRebateState extends AbstractState {
	public static final Map ALL = new HashMap();

	public static final CustomerRebateState WAITED = new CustomerRebateState("待审核", "00");
	public static final CustomerRebateState PASSED = new CustomerRebateState("审核通过 ", "10");	
	public static final CustomerRebateState FALURE = new CustomerRebateState("审核不通过 ", "20");
//	public static final CustomerRebateState NORMAL = new CustomerRebateState("正常", "01");
	public static final CustomerRebateState DISABLE = new CustomerRebateState("失效", "02");	
	
	@SuppressWarnings("unchecked")
	protected CustomerRebateState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static CustomerRebateState valueOf(String value) {
		CustomerRebateState type = (CustomerRebateState) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("流程状态错误！");
		}

		return type;
	}
}
