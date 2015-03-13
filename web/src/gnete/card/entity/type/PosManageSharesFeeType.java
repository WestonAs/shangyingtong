package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * @author aps-mjn
 */
public class PosManageSharesFeeType extends AbstractType {
	public static final Map ALL = new HashMap();
	
	/*
	运营中心与机具维护方分润方式
	*/
	public static final PosManageSharesFeeType FEE = new PosManageSharesFeeType("按交易金额固定比例", "1");
	public static final PosManageSharesFeeType TRADEMONEY = new PosManageSharesFeeType("按年交易金额分段调整", "2");
	public static final PosManageSharesFeeType SSUM = new PosManageSharesFeeType("按年交易金额分段累进调整","3");
	
	public static PosManageSharesFeeType valueOf(String value) {
		PosManageSharesFeeType type = (PosManageSharesFeeType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("没有该分润方式");
		}

		return type;
	}
	
	@SuppressWarnings("unchecked")
	protected PosManageSharesFeeType(String name, String value) {
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
