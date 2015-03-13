package gnete.card.entity.type;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @File: BankAcctType.java
 *
 * @description: 银行账户类型代码
 * 
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-lib
 * @version: 1.0
 * @since 1.0 2011-8-23
 */
public class BankAcctType extends AbstractType{
	public static final Map ALL = new HashMap();
	
	/**
	 * 00保得积分资金账户
	 * 01保得中间账户
	 * 02收益账户
	 */
	public static final BankAcctType BD_POINT_FUND_ACCT = new BankAcctType("保得积分资金账户", "00");
	public static final BankAcctType BD_MIDDLE_ACCT = new BankAcctType("保得中间帐户", "01");
	public static final BankAcctType BD_PROFIT_ACCT = new BankAcctType("收益账户", "02");
	
	@SuppressWarnings("unchecked")
	protected BankAcctType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static BankAcctType valueOf(String value) {
		BankAcctType type = (BankAcctType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的类型");
		}
		return type;
	}
	
	public static List getAll(){
		return getOrderedList(BankAcctType.ALL);
	}
	
}
