package gnete.card.entity.state;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

/**
 * @File:GiftDefState.java
 *
 * @description: 中奖状态
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-lib
 * @version: 1.0
 * @since 1.0 2010-8-18
 */
public class AwdState extends AbstractState{
	
	public static final Map ALL = new HashMap();

	public static final AwdState WAITED = new AwdState("待中奖", "00");
	public static final AwdState WON = new AwdState("已中奖 ", "01");	
	public static final AwdState EXCHANGED = new AwdState("已兑奖 ", "02");
	public static final AwdState RETRIEVE = new AwdState("未中奖已回收 ", "03");	
	public static final AwdState WITHDRAWAL = new AwdState("中奖已被退回 ", "04");
	public static final AwdState CANCEL = new AwdState("中奖已撤销 ", "05");
	
	
	protected AwdState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static AwdState valueOf(String value) {
		AwdState type = (AwdState) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("流程状态错误！");
		}
		
		return type;
	}
	
	public static List getAll(){
		return getOrderedList(AwdState.ALL);
	}
	
	public static List getAwdPrizeState(){
		Map params = new HashMap();
		params.put(EXCHANGED.getValue(), EXCHANGED);
		params.put(RETRIEVE.getValue(), RETRIEVE);
		params.put(WITHDRAWAL.getValue(), WITHDRAWAL);
		params.put(CANCEL.getValue(), CANCEL);		
		return getOrderedList(params);
	}
}
