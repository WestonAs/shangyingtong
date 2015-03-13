package gnete.card.entity.state;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

/**
 *  * @description: 高级折扣审核通用状态
 * <li>00：失效</li>
 * <li>01：生效</li>
 * <li>02：待审核</li>
 * <li>03：审核不通过</li>
 * 
 * @Project: CardWeb
 * @File: DrawDefineState.java
 * @See:
 * @author: aps-qfg
 * @modified:
 * @Email: aps-qfg@cnaps.com.cn
 * @Date: 2012-12-27下午6:08:04
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2012 版权所有
 * @version: V1.0
 */
public class GreatDiscntProtclDefState extends AbstractState {
	public static final Map ALL = new HashMap();

	public static final GreatDiscntProtclDefState EXPIRE = new GreatDiscntProtclDefState("失效", "00");
	public static final GreatDiscntProtclDefState EFFECT = new GreatDiscntProtclDefState("生效", "01");	
	public static final GreatDiscntProtclDefState WAIT = new GreatDiscntProtclDefState("待审核", "02");
	public static final GreatDiscntProtclDefState FAIL = new GreatDiscntProtclDefState("审核不通过", "03");
	
	@SuppressWarnings("unchecked")
	protected GreatDiscntProtclDefState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static GreatDiscntProtclDefState valueOf(String value) {
		GreatDiscntProtclDefState type = (GreatDiscntProtclDefState) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("流程状态错误！");
		}

		return type;
	}
	
	public static List getAll(){
		return getOrderedList(GreatDiscntProtclDefState.ALL);
	}
}
