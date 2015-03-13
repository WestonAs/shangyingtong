package gnete.card.entity.state;

import java.util.List;
import java.util.Map;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

/**
 * 
 * @author libaozhu
 * @date 2013-3-4
 */
public class RechargeState extends AbstractState {
	public static final Map ALL = new java.util.LinkedHashMap();

	public static final RechargeState WAIT_CHECK = new RechargeState("待审核", "10");
	public static final RechargeState CHECK_FAILURE = new RechargeState("审核不通过", "20");
	public static final RechargeState RECHARGED = new RechargeState("已充值", "30");

	public static RechargeState valueOf(String value) {
		RechargeState type = (RechargeState) ALL.get(value);

		if (type == null) {
			throw new RuntimeBizException("状态错误！");
		}

		return type;
	}

	@SuppressWarnings("unchecked")
	protected RechargeState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	public static List getAll(){
		return getOrderedList(RechargeState.ALL);
	}
}
