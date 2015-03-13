package gnete.card.entity.state;

import java.util.HashMap;
import java.util.Map;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

/**
 * 商户类型状态
 * 
 * @author aps-lih
 */
public class MerchTypeState extends AbstractState {
	public static final Map ALL = new HashMap();

	public static final MerchTypeState NORMAL = new MerchTypeState("正常", "00");
	public static final MerchTypeState CANCEL = new MerchTypeState("注销", "10");	
	
	@SuppressWarnings("unchecked")
	protected MerchTypeState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static MerchTypeState valueOf(String value) {
		MerchTypeState type = (MerchTypeState) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("商户类型状态错误！");
		}

		return type;
	}
}
