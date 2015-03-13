package gnete.card.entity.flag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;



public class BalanceFlag extends AbstractType{
	
	public static final Map ALL = new HashMap();
	
	/**
	 * 试算平衡
	 * 0: 平衡
	 * 1: 不平衡
	 * 9: 出错
	 */
	public static final BalanceFlag BALANCE = new BalanceFlag("平衡", "0");
	public static final BalanceFlag UNBALANCE = new BalanceFlag("不平衡", "1");
	public static final BalanceFlag FAULT = new BalanceFlag("出错", "9");
	
	protected BalanceFlag(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static BalanceFlag valueOf(String value) {
		BalanceFlag type = (BalanceFlag) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的标志");
		}
		return type;
	}
	
	public static List getAll(){
		return getOrderedList(BalanceFlag.ALL);
	}

}
