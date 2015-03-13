package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;


public class TradeType extends AbstractType{
	public static final Map ALL = new HashMap();
	private String beanName;
	public static final TradeType WITHDRAW = new TradeType("提现", "10", "withdrawService");
	public static final TradeType TRANS = new TradeType("转账", "20", "transBillService");
	public static final TradeType PAY = new TradeType("支付", "30", "payBillService");
	@SuppressWarnings("unchecked")
	
	protected TradeType(String name, String value, String beanName) {
		super(name, value);
		this.beanName = beanName;
		ALL.put(value, this);
	}
	public static TradeType valueOf(String value) {
		TradeType type = (TradeType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的类型");
		}
		return type;
	}
	
	public static List getAll(){
		return getOrderedList(TradeType.ALL);
	}
	public String getBeanName() {
		return beanName;
	}
	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}
}
