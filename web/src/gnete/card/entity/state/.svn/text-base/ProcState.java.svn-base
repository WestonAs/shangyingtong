package gnete.card.entity.state;

import java.util.HashMap;
import java.util.Map;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

/**
 * @File:ProcState.java
 *
 * @description: 交易处理状态
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-lib
 * @version: 1.0
 * @since 1.0 2010-8-13
 */

public class ProcState extends AbstractState{
	
	public static final Map ALL = new HashMap();
	
	public static final ProcState RECEIVED = new ProcState("已接收", "00");
	public static final ProcState DEDSUCCESS = new ProcState("扣款成功 ", "01");	
	public static final ProcState DEDFALURE = new ProcState("扣款失败 ", "02");
	public static final ProcState REPEALED = new ProcState("已撤销", "03");
	public static final ProcState REFUNDED = new ProcState("已冲正 ", "04");	
	public static final ProcState FREEZED = new ProcState("已冻结 ", "05");	
	public static final ProcState SETTLED = new ProcState("已清算 ", "10");
	public static final ProcState PROCFAILED = new ProcState("处理失败", "11");
	public static final ProcState ADJ = new ProcState("已调账", "12");

	protected ProcState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static ProcState valueOf(String value) {
		ProcState type = (ProcState) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("流程状态错误！");
		}
		
		return type;
	}
	
	public static Map getBusiState(){
		Map params = new HashMap();
		params.put(RECEIVED.getValue(), RECEIVED);
		params.put(DEDSUCCESS.getValue(), DEDSUCCESS);
		params.put(DEDFALURE.getValue(), DEDFALURE);
		params.put(REPEALED.getValue(), REPEALED);
		params.put(REFUNDED.getValue(), REFUNDED);
		return params;
	}
	
	//促销活动
	public static Map getPromtState(){
		Map params = new HashMap();
		params.put(DEDSUCCESS.getValue(), DEDSUCCESS);
		params.put(REPEALED.getValue(), REPEALED);
		params.put(REFUNDED.getValue(), REFUNDED);
		return params;
	}
	

}
