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
public class ChnlTradeState extends AbstractState {
	public static final Map ALL = new java.util.LinkedHashMap();

	public static final ChnlTradeState UN_RETURN = new ChnlTradeState("未回盘", "10");
	public static final ChnlTradeState RETURNED = new ChnlTradeState("已回盘", "20");
	public static final ChnlTradeState MANUAL = new ChnlTradeState("手工置结果", "30");
	public static ChnlTradeState valueOf(String value) {
		ChnlTradeState type = (ChnlTradeState) ALL.get(value);

		if (type == null) {
			throw new RuntimeBizException("状态错误！");
		}

		return type;
	}

	@SuppressWarnings("unchecked")
	protected ChnlTradeState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	public static List getAll(){
		return getOrderedList(ChnlTradeState.ALL);
	}
}
