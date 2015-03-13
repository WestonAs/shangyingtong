package gnete.card.entity.state;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

public class RmaState extends AbstractState{

	/**
	 * 00 未划付
	 * 01已划付
	 */
	
	public static final Map ALL = new HashMap();
	
	public static final RmaState UN_REMIT = new RmaState("未划付", "00");
	public static final RmaState REMITED = new RmaState("已划付 ", "01");	
	
	protected RmaState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static RmaState valueOf(String value) {
		RmaState type = (RmaState) ALL.get(value);

		if (type == null) {
			throw new RuntimeBizException("流程状态错误！");
		}

		return type;
	}

	public static List getAll() {
		return getOrderedList(RmaState.ALL);
	}

}
