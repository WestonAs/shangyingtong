package gnete.card.entity.state;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

import java.util.HashMap;
import java.util.Map;

/**
 * @File:GiftDefState.java
 *
 * @description: 礼品定义申请状态
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-lib
 * @version: 1.0
 * @since 1.0 2010-8-1
 */
public class GiftDefState extends AbstractState{
	public static final Map ALL = new HashMap();

	public static final GiftDefState WAITED = new GiftDefState("待审核", "00");
	public static final GiftDefState PASSED = new GiftDefState("审核通过 ", "10");	
	public static final GiftDefState FALURE = new GiftDefState("审核不通过 ", "20");
	
	@SuppressWarnings("unchecked")
	protected GiftDefState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static GiftDefState valueOf(String value) {
		GiftDefState type = (GiftDefState) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("流程状态错误！");
		}
		
		return type;
	}
	
}
