package gnete.card.entity.state;

import java.util.HashMap;
import java.util.Map;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

/**
 * 代理关系状态
 * 
 * @author aps-lih
 */
public class ProxyState extends AbstractState {
	public static final Map ALL = new HashMap();

	public static final ProxyState NORMAL = new ProxyState("正常", "00");
	public static final ProxyState CANCEL = new ProxyState("注销", "10");	
	
	@SuppressWarnings("unchecked")
	protected ProxyState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static ProxyState valueOf(String value) {
		ProxyState type = (ProxyState) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("代理关系状态错误！");
		}

		return type;
	}
}
