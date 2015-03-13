package gnete.card.entity.flag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

/**
 * @author: aps-lih
 */
public class IsFileFlag extends AbstractState {
	public static final Map ALL = new HashMap();

	public static final IsFileFlag YES = new IsFileFlag("是", "1");
	public static final IsFileFlag NO = new IsFileFlag("否", "0");

	@SuppressWarnings("unchecked")
	protected IsFileFlag(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static IsFileFlag valueOf(String value) {
		IsFileFlag type = (IsFileFlag) ALL.get(value);

		if (type == null) {
			throw new RuntimeBizException("状态错误！");
		}

		return type;
	}

	public static List getAll() {
		return getOrderedList(IsFileFlag.ALL);
	}
}
