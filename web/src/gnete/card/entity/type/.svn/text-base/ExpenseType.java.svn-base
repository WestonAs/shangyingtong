package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

public class ExpenseType extends AbstractType {
	
public static final Map ALL = new HashMap();
	
	/**
	 * 消费类型
	 */
	public static final ExpenseType CHECKOUT = new ExpenseType("收银台","0");
	public static final ExpenseType CUSTOMER = new ExpenseType("客户服务台","1");
	public static final ExpenseType CASH = new ExpenseType("收银部终端","2");
	public static final ExpenseType FINANCE = new ExpenseType("财务部终端","3");
	public static final ExpenseType OTHER = new ExpenseType("其他类型终端","4");
	
	@SuppressWarnings("unchecked")
	protected ExpenseType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static ExpenseType valueOf(String value) {
		ExpenseType type = (ExpenseType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的类型");
		}
		return type;
	}
	
	public static List getAll(){
		return getOrderedList(ExpenseType.ALL);
	}

}
