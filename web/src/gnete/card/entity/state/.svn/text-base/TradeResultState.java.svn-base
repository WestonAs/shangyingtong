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
public class TradeResultState extends AbstractState {
	public static final Map ALL = new java.util.LinkedHashMap();

	public static final TradeResultState SUCCESS = new TradeResultState("成功", "S");
	public static final TradeResultState FAILURE = new TradeResultState("失败", "F");
	public static TradeResultState valueOf(String value) {
		TradeResultState type = (TradeResultState) ALL.get(value);

		if (type == null) {
			throw new RuntimeBizException("状态错误！");
		}

		return type;
	}

	@SuppressWarnings("unchecked")
	protected TradeResultState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	public static List getAll(){
		return getOrderedList(TradeResultState.ALL);
	}
}
