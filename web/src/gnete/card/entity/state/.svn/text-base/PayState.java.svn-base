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
public class PayState extends AbstractState {
	public static final Map ALL = new java.util.LinkedHashMap();

	public static final PayState WAIT_CHECK = new PayState("待审核", "10");
	public static final PayState CHECK_FAILURE = new PayState("审核不通过", "20");
	public static final PayState PAYING = new PayState("支付中", "30");
	public static final PayState FAILED = new PayState("支付失败", "40");
	public static final PayState FINISH = new PayState("支付成功", "50");
	public static PayState valueOf(String value) {
		PayState type = (PayState) ALL.get(value);

		if (type == null) {
			throw new RuntimeBizException("状态错误！");
		}

		return type;
	}

	@SuppressWarnings("unchecked")
	protected PayState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	public static List getAll(){
		return getOrderedList(PayState.ALL);
	}
}
