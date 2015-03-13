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
public class TransState extends AbstractState {
	public static final Map ALL = new java.util.LinkedHashMap();

	public static final TransState WAIT_CHECK = new TransState("待审核", "10");
	public static final TransState CHECK_FAILURE = new TransState("审核不通过", "20");
	public static final TransState PAYING = new TransState("支付中", "30");
	public static final TransState FAILED = new TransState("转账失败", "40");
	public static final TransState FINISH = new TransState("转账成功", "50");
	public static TransState valueOf(String value) {
		TransState type = (TransState) ALL.get(value);

		if (type == null) {
			throw new RuntimeBizException("状态错误！");
		}

		return type;
	}

	@SuppressWarnings("unchecked")
	protected TransState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	public static List getAll(){
		return getOrderedList(TransState.ALL);
	}
}
