package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

public class PtExchgType extends AbstractType{
	
	public static final Map ALL = new HashMap();
	
	/*
	积分兑换类型:
		12-积分兑换礼品
		13-积分返券
		14-积分返利
	*/
	public static final PtExchgType PTEXCHGGIFT = new PtExchgType("积分兑换礼品", "12");
	public static final PtExchgType PTEXCHGCOUPON = new PtExchgType("积分返券", "13");
	public static final PtExchgType PTEXCHGMONEY = new PtExchgType("积分返利", "14");
	
	protected PtExchgType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static PtExchgType valueOf(String value) {
		PtExchgType type = (PtExchgType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的类型");
		}
		return type;
	}
	
	public static List getAll(){
		return getOrderedList(PtExchgType.ALL);
	}

}
