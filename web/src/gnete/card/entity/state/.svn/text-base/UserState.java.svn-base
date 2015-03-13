package gnete.card.entity.state;

import java.util.HashMap;
import java.util.Map;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

/**
 * 用户状态
 * 
 * @author aps-lih
 */
public class UserState extends AbstractState {
	public static final Map ALL = new HashMap();

	public static final UserState NORMAL = new UserState("离线", "00");
	public static final UserState ONLINE = new UserState("在线", "01");	
	public static final UserState CANCEL = new UserState("注销", "02");	
	
	@SuppressWarnings("unchecked")
	protected UserState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static UserState valueOf(String value) {
		UserState type = (UserState) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("流程状态错误！");
		}

		return type;
	}
}
