package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * @author aps-mjn
 */
public class MerchProxySharesFeeType extends AbstractType {
	public static final Map ALL = new HashMap();
	
	/*
	发卡机构与商户代理商分润方式
	*/
	public static final MerchProxySharesFeeType FEE = new MerchProxySharesFeeType("按金额固定比例  ", "1");
	public static final MerchProxySharesFeeType TRADEMONEY = new MerchProxySharesFeeType("金额分段比例", "2");
	public static final MerchProxySharesFeeType SSUM = new MerchProxySharesFeeType("按金额段阶梯收费", "3");
	
	public static MerchProxySharesFeeType valueOf(String value) {
		MerchProxySharesFeeType type = (MerchProxySharesFeeType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("没有该分润方式");
		}

		return type;
	}
	
	@SuppressWarnings("unchecked")
	protected MerchProxySharesFeeType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static List getList() {
		return getOrderedList(ALL);
	}
	
	public static List getFeeAndTradeMoneyType() {
		Map params = new HashMap();
		params.put(FEE.getValue(), FEE);
		params.put(TRADEMONEY.getValue(), TRADEMONEY);
		
		return getOrderedList(params);
	}
}
