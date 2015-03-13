package gnete.card.entity.flag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

/**
 * @author: aps-lih
 */
public class YesOrNoFlag extends AbstractState {
	public static final Map ALL = new HashMap();

	public static final YesOrNoFlag YES = new YesOrNoFlag("是", "Y");
	public static final YesOrNoFlag NO = new YesOrNoFlag("否", "N");

	@SuppressWarnings("unchecked")
	protected YesOrNoFlag(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static YesOrNoFlag valueOf(String value) {
		YesOrNoFlag type = (YesOrNoFlag) ALL.get(value);

		if (type == null) {
			throw new RuntimeBizException("状态错误！");
		}

		return type;
	}

	public static List getAll() {
		return getOrderedList(YesOrNoFlag.ALL);
	}
}
