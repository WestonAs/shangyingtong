package gnete.card.entity.flag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

/**
 * @File: RiskFlag.java
 *
 * @description: 风控标志
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2011-1-10
 */
public class RiskFlag extends AbstractState {
	public static final Map ALL = new HashMap();

	public static final RiskFlag NOFLAG = new RiskFlag("无", "0");
	public static final RiskFlag WARNING = new RiskFlag("预警", "1");
	public static final RiskFlag REFUSED = new RiskFlag("拒绝", "9");

	@SuppressWarnings("unchecked")
	protected RiskFlag(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static RiskFlag valueOf(String value) {
		RiskFlag type = (RiskFlag) ALL.get(value);

		if (type == null) {
			throw new RuntimeBizException("状态错误！");
		}

		return type;
	}

	public static List getAll() {
		return getOrderedList(RiskFlag.ALL);
	}
}
