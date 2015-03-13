package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * @author aps-mjn
 */
public class CenterProxySharesFeeType extends AbstractType {
	public static final Map ALL = new HashMap();
	
	/*
	运营机构与运营机构代理商分润方式
	*/
	public static final CenterProxySharesFeeType FEE = new CenterProxySharesFeeType("按交易金额固定比例", "1");
	public static final CenterProxySharesFeeType TRADEMONEY = new CenterProxySharesFeeType("按年交易金额分段调整", "2");
	public static final CenterProxySharesFeeType SSUM = new CenterProxySharesFeeType("按年交易金额分段累进调整","3");
	
	public static CenterProxySharesFeeType valueOf(String value) {
		CenterProxySharesFeeType type = (CenterProxySharesFeeType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("没有该分润方式");
		}

		return type;
	}
	
	@SuppressWarnings("unchecked")
	protected CenterProxySharesFeeType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static List getList() {
		return getOrderedList(ALL);
	}
	
	public static List getFeeType() {
		Map params = new HashMap();
		params.put(FEE.getValue(), FEE);
		return getOrderedList(params);
	}
	
}
