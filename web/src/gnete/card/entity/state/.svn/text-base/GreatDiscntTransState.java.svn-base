package gnete.card.entity.state;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

/**
 *  高级折扣交易状态
 *  00待返还
 *  01已返还
 *  02返还失败
 *  03已撤销
 * @Project: CardWeb
 * @File: GreatDiscntTransState.java
 * @See:
 * @author: aps-qfg
 * @modified:
 * @Email: aps-qfg@cnaps.com.cn
 * @Date: 2013-1-31上午9:44:12
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2013 版权所有
 * @version: V1.0
 */
public class GreatDiscntTransState extends AbstractState {
	public static final Map ALL = new HashMap();

	public static final GreatDiscntTransState WAIT = new GreatDiscntTransState("待返还", "00");
	public static final GreatDiscntTransState SUCCESS = new GreatDiscntTransState("已返还", "01");	
	public static final GreatDiscntTransState FAIL = new GreatDiscntTransState("返还失败", "02");
	public static final GreatDiscntTransState CANCEL = new GreatDiscntTransState("已撤销", "03");
	
	@SuppressWarnings("unchecked")
	protected GreatDiscntTransState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static GreatDiscntTransState valueOf(String value) {
		GreatDiscntTransState type = (GreatDiscntTransState) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("流程状态错误！");
		}

		return type;
	}
	
	public static List getAll(){
		return getOrderedList(GreatDiscntTransState.ALL);
	}
}
