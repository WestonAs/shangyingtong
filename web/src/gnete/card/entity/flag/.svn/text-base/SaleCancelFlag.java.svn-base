package gnete.card.entity.flag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

/**
 * @File: DepositCancelFlag.java
 *
 * @description: 售卡撤销标志
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2011-11-22
 */
public class SaleCancelFlag extends AbstractState {
	public static final Map ALL = new HashMap();

	public static final SaleCancelFlag NORMAL = new SaleCancelFlag("正常售卡", "0");
	public static final SaleCancelFlag CANCEL = new SaleCancelFlag("售卡撤销", "1");

	@SuppressWarnings("unchecked")
	protected SaleCancelFlag(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static SaleCancelFlag valueOf(String value) {
		SaleCancelFlag type = (SaleCancelFlag) ALL.get(value);

		if (type == null) {
			throw new RuntimeBizException("标志错误！");
		}

		return type;
	}

	public static List getAll() {
		return getOrderedList(SaleCancelFlag.ALL);
	}
}
