package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * @author aps-mjn
 */
public class SaleProxyRtnFeeType extends AbstractType {
	public static final Map ALL = new HashMap();
	
	/*
	售卡代理商返利方式
	*/
	//public static final SaleProxyRtnFeeType EACH = new SaleProxyRtnFeeType("按交易笔数","0");
	public static final SaleProxyRtnFeeType FEE = new SaleProxyRtnFeeType("按交易金额固定比例", "1");
	public static final SaleProxyRtnFeeType TRADEMONEY = new SaleProxyRtnFeeType("按年交易金额分段调整", "2");
//	public static final SaleProxyRtnFeeType SSUM = new SaleProxyRtnFeeType("按年交易金额分段累进调整","3");
	
	public static SaleProxyRtnFeeType valueOf(String value) {
		SaleProxyRtnFeeType type = (SaleProxyRtnFeeType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("没有该返利方式");
		}

		return type;
	}
	
	@SuppressWarnings("unchecked")
	protected SaleProxyRtnFeeType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static List getList() {
		return getOrderedList(ALL);
	}
	
}
