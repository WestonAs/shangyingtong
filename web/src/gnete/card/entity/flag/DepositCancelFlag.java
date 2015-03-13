package gnete.card.entity.flag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

/**
 * @File: DepositCancelFlag.java
 *
 * @description: 充值撤销标志
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2011-3-16
 */
public class DepositCancelFlag extends AbstractState {
	public static final Map ALL = new HashMap();

	public static final DepositCancelFlag NORMAL = new DepositCancelFlag("正常充值", "0");
	public static final DepositCancelFlag CANCEL = new DepositCancelFlag("充值撤销", "1");

	@SuppressWarnings("unchecked")
	protected DepositCancelFlag(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static DepositCancelFlag valueOf(String value) {
		DepositCancelFlag type = (DepositCancelFlag) ALL.get(value);

		if (type == null) {
			throw new RuntimeBizException("状态错误！");
		}

		return type;
	}

	public static List getAll() {
		return getOrderedList(DepositCancelFlag.ALL);
	}
}
