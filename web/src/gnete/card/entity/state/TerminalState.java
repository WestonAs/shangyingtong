package gnete.card.entity.state;

import java.util.HashMap;
import java.util.Map;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

/**
 * 商户状态
 * 
 * @author aps-lih
 */
public class TerminalState extends AbstractState {
	public static final Map ALL = new HashMap();

	public static final TerminalState NORMAL = new TerminalState("正常", "0");
	public static final TerminalState STOP = new TerminalState("停用", "1");	
	
	@SuppressWarnings("unchecked")
	protected TerminalState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static TerminalState valueOf(String value) {
		TerminalState type = (TerminalState) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("商户状态错误！");
		}

		return type;
	}
}
