package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

public class ChlFeeType extends AbstractType{

	public static final Map ALL = new HashMap();

	public static final ChlFeeType FIX_MONEY = new ChlFeeType("固定金额", "1");
	public static final ChlFeeType FIX_RATE = new ChlFeeType("固定比率", "2");
	public static final ChlFeeType TRADE_MONEY = new ChlFeeType("分段金额","3");
	public static final ChlFeeType TRADE_RATE = new ChlFeeType("分段比率","4");
	public static final ChlFeeType ACCUMULATIVE_RATE = new ChlFeeType("阶梯收费","6");
	public static final ChlFeeType EXTRA_FIX_COST = new ChlFeeType("附加固定金额","A");
		
	protected ChlFeeType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static List getList() {
		return getOrderedList(ALL);
	}
	
	public static List getForChlFee(){
		Map params = new HashMap();
		params.put(FIX_MONEY.getValue(), FIX_MONEY);
		params.put(FIX_RATE.getValue(), FIX_RATE);
		params.put(TRADE_MONEY.getValue(), TRADE_MONEY);
		params.put(TRADE_RATE.getValue(), TRADE_RATE);
		params.put(ACCUMULATIVE_RATE.getValue(), ACCUMULATIVE_RATE);
		return getOrderedList(params);
	}
	
	public static List getTradeChlFee(){
		Map params = new HashMap();
		params.put(TRADE_MONEY.getValue(), TRADE_MONEY);
		params.put(TRADE_RATE.getValue(), TRADE_RATE);
		return getOrderedList(params);
	}
	
	public static ChlFeeType valueOf(String value) {
		ChlFeeType type = (ChlFeeType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("没有该计费类型");
		}

		return type;
	}

}
