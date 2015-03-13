package gnete.card.entity.flag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

public class ecouponTypeFlag extends AbstractState {
	public static final Map ALL = new HashMap();

	public static final ecouponTypeFlag MULT = new ecouponTypeFlag("多次消费券", "0");
	public static final ecouponTypeFlag ONE = new ecouponTypeFlag("一次性消费券", "1");

	@SuppressWarnings("unchecked")
	protected ecouponTypeFlag(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static ecouponTypeFlag valueOf(String value) {
		ecouponTypeFlag type = (ecouponTypeFlag) ALL.get(value);

		if (type == null) {
			throw new RuntimeBizException("状态错误！");
		}

		return type;
	}

	public static List getAll() {
		return getOrderedList(ecouponTypeFlag.ALL);
	}

}
