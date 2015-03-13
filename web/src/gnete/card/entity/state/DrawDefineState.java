package gnete.card.entity.state;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

/**
 *  * @description: 抽奖活动审核通用状态
 * <li>00: 待审核</li>
 * <li>10: 审核通过</li>
 * <li>20: 审核失败</li>
 * <li>11: 已抽奖</li>
 * <li>21: 未抽奖</li>
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
public class DrawDefineState extends AbstractState {
	public static final Map ALL = new HashMap();

	public static final DrawDefineState WAITED = new DrawDefineState("待审核", "00");
	public static final DrawDefineState PASSED = new DrawDefineState("审核通过", "10");	
	public static final DrawDefineState FAILED = new DrawDefineState("审核不通过", "20");
	public static final DrawDefineState DRAWED_N = new DrawDefineState("未抽奖", "11");
	public static final DrawDefineState DRAWED_Y = new DrawDefineState("已抽奖", "21");
	
	@SuppressWarnings("unchecked")
	protected DrawDefineState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static DrawDefineState valueOf(String value) {
		DrawDefineState type = (DrawDefineState) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("流程状态错误！");
		}

		return type;
	}
	
	public static List getAll(){
		return getOrderedList(DrawDefineState.ALL);
	}
}
