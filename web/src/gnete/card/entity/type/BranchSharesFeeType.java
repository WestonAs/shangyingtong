package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * @author aps-mjn
 */
public class BranchSharesFeeType extends AbstractType {
	public static final Map ALL = new HashMap();
	
	/*
	运营中心与分支机构分润
	*/
	public static final BranchSharesFeeType FEE = new BranchSharesFeeType("按交易金额固定比例", "1");
	public static final BranchSharesFeeType TRADEMONEY = new BranchSharesFeeType("按年交易金额分段调整", "2");
	public static final BranchSharesFeeType SSUM = new BranchSharesFeeType("按年交易金额分段累进调整","3");
	
	public static BranchSharesFeeType valueOf(String value) {
		BranchSharesFeeType type = (BranchSharesFeeType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("没有该计费方式");
		}

		return type;
	}
	
	@SuppressWarnings("unchecked")
	protected BranchSharesFeeType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static List getList() {
		return getOrderedList(ALL);
	}
	
}
