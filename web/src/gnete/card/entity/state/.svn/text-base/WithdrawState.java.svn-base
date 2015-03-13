package gnete.card.entity.state;

import java.util.List;
import java.util.Map;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

/**
 * 
 * @author libaozhu
 * @date 2013-3-7
 */
public class WithdrawState extends AbstractState {
	public static final Map ALL = new java.util.LinkedHashMap();

	public static final WithdrawState WAIT_CHECK = new WithdrawState("待审核", "10");
	public static final WithdrawState CHECK_FAILURE = new WithdrawState("审核不通过", "20");
	public static final WithdrawState PAYING = new WithdrawState("支付中", "30");
	public static final WithdrawState FAILED = new WithdrawState("提现失败", "40");
	public static final WithdrawState FINISH = new WithdrawState("已提现", "50");
	public static WithdrawState valueOf(String value) {
		WithdrawState type = (WithdrawState) ALL.get(value);

		if (type == null) {
			throw new RuntimeBizException("状态错误！");
		}

		return type;
	}

	@SuppressWarnings("unchecked")
	protected WithdrawState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	public static List getAll(){
		return getOrderedList(WithdrawState.ALL);
	}
}
