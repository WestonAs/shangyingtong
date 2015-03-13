package gnete.card.entity.state;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

/**
 * 虚账体系状态
 * @author libaozhu
 * @date 2013-3-1
 */
public class AccountSystemState extends AbstractState{
	
	public static final Map ALL = new HashMap();

	public static final AccountSystemState NORMAL = new AccountSystemState("正常", "00");
	public static final AccountSystemState STOP = new AccountSystemState("停用", "01");
	@SuppressWarnings("unchecked")
	protected AccountSystemState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static AccountSystemState valueOf(String value) {
		AccountSystemState type = (AccountSystemState) ALL.get(value);
		if (type == null) {
			throw new RuntimeBizException("状态错误！");
		}
		return type;
	}
	
	public static List getAll(){
		return getOrderedList(AccountSystemState.ALL);
	}
	
}
