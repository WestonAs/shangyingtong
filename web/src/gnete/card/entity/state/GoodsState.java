package gnete.card.entity.state;

import java.util.HashMap;
import java.util.Map;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

/**
 * 退货状态
 * 
 * @author aps-lih
 */
public class GoodsState extends AbstractState {
	public static final Map ALL = new HashMap();

	public static final GoodsState NOT_RETURNED = new GoodsState("未退货", "00");
	public static final GoodsState HAS_RETURNED = new GoodsState("已退货", "01");	
	
	@SuppressWarnings("unchecked")
	protected GoodsState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static GoodsState valueOf(String value) {
		GoodsState type = (GoodsState) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("退货状态错误！");
		}

		return type;
	}
}
