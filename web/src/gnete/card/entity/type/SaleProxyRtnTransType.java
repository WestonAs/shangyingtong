package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * @author aps-mjn
 */
public class SaleProxyRtnTransType extends AbstractType {
	public static final Map ALL = new HashMap();
	
	/*
	售卡代理商交易类型
	*/
	public static final SaleProxyRtnTransType CHARGE = new SaleProxyRtnTransType("售卡", "0");
	public static final SaleProxyRtnTransType SELL = new SaleProxyRtnTransType("充值", "1");
	
	
	public static SaleProxyRtnTransType valueOf(String value) {
		SaleProxyRtnTransType type = (SaleProxyRtnTransType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("没有该交易类型");
		}

		return type;
	}
	
	@SuppressWarnings("unchecked")
	protected SaleProxyRtnTransType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static List getList() {
		return getOrderedList(ALL);
	}
	
}
