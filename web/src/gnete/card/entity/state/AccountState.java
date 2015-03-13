package gnete.card.entity.state;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

/**
 * @File:GiftDefState.java
 *
 * @description: 卡状态
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-lib
 * @version: 1.0
 * @since 1.0 2010-8-3
 */
public class AccountState extends AbstractState{
	
	public static final Map ALL = new HashMap();

	public static final AccountState NORMAL = new AccountState("正常", "00");
	public static final AccountState WAIT_CHECK = new AccountState("待审核", "01");
	public static final AccountState CANCELED = new AccountState("注销", "03");
	public static final AccountState FREEZED = new AccountState("冻结", "04");
	public static final AccountState CHECK_FAILED = new AccountState("审核不通过", "05");
	public static final AccountState SYSTEM_STOP = new AccountState("所属体系已停用", "06");
	@SuppressWarnings("unchecked")
	protected AccountState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static AccountState valueOf(String value) {
		AccountState type = (AccountState) ALL.get(value);
		if (type == null) {
			throw new RuntimeBizException("状态错误！");
		}
		return type;
	}
	
	public static List getAll(){
		return getOrderedList(AccountState.ALL);
	}
	
}
