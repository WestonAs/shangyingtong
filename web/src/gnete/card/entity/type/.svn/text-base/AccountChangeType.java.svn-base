package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * 账户变动类型
 * @author libaozhu
 * @date 2013-2-26
 */
public class AccountChangeType extends AbstractType{
	public static final Map ALL = new HashMap();
	
	public static final AccountChangeType RECHARGE = new AccountChangeType("充值", "10");
	public static final AccountChangeType WITHDRAW = new AccountChangeType("提现", "20");
	public static final AccountChangeType FREEZE = new AccountChangeType("冻结", "30");
	public static final AccountChangeType UNFREEZE = new AccountChangeType("解冻", "40");
	public static final AccountChangeType TRANS = new AccountChangeType("转账", "50");
	public static final AccountChangeType RECEIVE = new AccountChangeType("收款", "60");
	public static final AccountChangeType PAY = new AccountChangeType("支付", "70");
	public static final AccountChangeType REFUND = new AccountChangeType("退款", "80");
	@SuppressWarnings("unchecked")
	protected AccountChangeType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static AccountChangeType valueOf(String value) {
		AccountChangeType type = (AccountChangeType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("类型错误");
		}
		return type;
	}
	
	public static List getAll(){
		return getValueOrderedList(AccountChangeType.ALL);
	}
	
}
