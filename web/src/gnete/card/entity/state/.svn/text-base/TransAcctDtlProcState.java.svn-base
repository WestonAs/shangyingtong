package gnete.card.entity.state;

import java.util.HashMap;
import java.util.Map;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

/**
 * @File:TransAcctDtlProcState.java
 *
 * @description: 交易账户明细处理状态
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-lib
 * @version: 1.0
 * @since 1.0 2011-02-17
 */

public class TransAcctDtlProcState extends AbstractState{
	public static final Map ALL = new HashMap();
	
	public static final TransAcctDtlProcState DEDSUCCESS = new TransAcctDtlProcState("扣款成功", "01");	
	public static final TransAcctDtlProcState PART_RETURN_GOODS = new TransAcctDtlProcState("已部分退货", "02");	
	public static final TransAcctDtlProcState REPEALED = new TransAcctDtlProcState("已撤销", "03");
	public static final TransAcctDtlProcState REFUNDED = new TransAcctDtlProcState("已冲正", "04");	
	public static final TransAcctDtlProcState RETURN_GOODS = new TransAcctDtlProcState("已退货", "05");	
	public static final TransAcctDtlProcState ADJ = new TransAcctDtlProcState("已调账", "06");	
	
	protected TransAcctDtlProcState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static TransAcctDtlProcState valueOf(String value) {
		TransAcctDtlProcState type = (TransAcctDtlProcState) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("流程状态错误！");
		}
		return type;
	}

}
