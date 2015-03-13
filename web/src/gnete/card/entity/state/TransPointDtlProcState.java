package gnete.card.entity.state;

import java.util.HashMap;
import java.util.Map;
import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;
/**
 * @File:TransPointDtlProcState.java
 *
 * @description: 交易积分明细处理状态
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-lib
 * @version: 1.0
 * @since 1.0 2011-02-17
 */
public class TransPointDtlProcState extends AbstractState{

	public static final Map ALL = new HashMap();
	
	public static final TransPointDtlProcState CHGSUCCESS = new TransPointDtlProcState("变动成功", "01");	
	public static final TransPointDtlProcState REPEALED = new TransPointDtlProcState("已撤销", "03");
	public static final TransPointDtlProcState REFUNDED = new TransPointDtlProcState("已冲正", "04");	
	public static final TransPointDtlProcState RETURN_GOODS = new TransPointDtlProcState("已退货", "05");	
		
	protected TransPointDtlProcState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static TransPointDtlProcState valueOf(String value) {
		TransPointDtlProcState type = (TransPointDtlProcState) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("流程状态错误！");
		}
		return type;
	}

}
