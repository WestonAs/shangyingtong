package gnete.card.entity.state;

import java.util.HashMap;
import java.util.Map;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

/**
 * 支付方式状态
 * 
 * @author aps-lih
 */
public class PayTypeCodeState extends AbstractState {
	public static final Map ALL = new HashMap();

	public static final PayTypeCodeState NORMAL = new PayTypeCodeState("正常", "00");
	public static final PayTypeCodeState CANCEL = new PayTypeCodeState("注销", "10");	
	
	@SuppressWarnings("unchecked")
	protected PayTypeCodeState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static PayTypeCodeState valueOf(String value) {
		PayTypeCodeState type = (PayTypeCodeState) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("支付方式状态错误！");
		}

		return type;
	}
}
