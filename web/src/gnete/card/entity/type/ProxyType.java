package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * @author aps-lih
 */
public class ProxyType extends AbstractType {
	public static final Map ALL = new HashMap();
	
	/*
	代理类型代码:
		11 运营机构代理、
		21 发卡机构商户代理
		22 发卡机构售卡代理
	*/

	public static final ProxyType AGENT = new ProxyType("运营机构代理", "11");
	public static final ProxyType MERCHANT = new ProxyType("发卡机构商户代理", "21");
	public static final ProxyType SELL = new ProxyType("发卡机构售卡代理", "22");
	
	
	@SuppressWarnings("unchecked")
	protected ProxyType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static ProxyType valueOf(String value) {
		ProxyType type = (ProxyType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("�˻�����������");
		}

		return type;
	}
	
	public static List getAll(){
		return getOrderedList(ProxyType.ALL);
	}
	
	public static List getForAgent(){
		Map params = new HashMap();
		params.put(AGENT.getValue(), AGENT);
		return getOrderedList(params);
	}
	
	public static List getForCard(){
		Map params = new HashMap();
		params.put(MERCHANT.getValue(), MERCHANT);
		params.put(SELL.getValue(), SELL);
		return getOrderedList(params);
	}
	
	public static List getForMerch(){
		Map params = new HashMap();
		params.put(MERCHANT.getValue(), MERCHANT);
		return getOrderedList(params);
	}
	
	public static List getForSell(){
		Map params = new HashMap();
		params.put(SELL.getValue(), SELL);
		return getOrderedList(params);
	}
}
