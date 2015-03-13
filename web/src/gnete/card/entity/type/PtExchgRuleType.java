package gnete.card.entity.type;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PtExchgRuleType extends AbstractType{
	public static final Map ALL = new HashMap();
	
	/*
	积分兑换规则类型:
		01-使用积分兑换返利，积分子类型见积分子类型参数，满积分参数起兑，兑换率见《积分类型定义》表
		02-使用积分兑换返利，积分子类型见积分子类型参数，每次只能兑换积分参数的整数倍，兑换率见《积分类型定义》表
		03-按兑换规则兑换
	*/
	public static final PtExchgRuleType FULLEXCHANGE = new PtExchgRuleType("满积分起兑", "01");
	public static final PtExchgRuleType TIMESEXCHANGE = new PtExchgRuleType("积分整数倍兑换", "02");
	public static final PtExchgRuleType EXCBYRULE = new PtExchgRuleType("按兑换规则兑换", "03");
	
	@SuppressWarnings("unchecked")
	protected PtExchgRuleType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static PtExchgRuleType valueOf(String value) {
		PtExchgRuleType type = (PtExchgRuleType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的类型");
		}
		return type;
	}
	
	public static List getAll(){
		return getOrderedList(PtExchgRuleType.ALL);
	}
	
	public static List getFullExchange(){
		Map params = new HashMap();
		params.put(FULLEXCHANGE.getValue(), FULLEXCHANGE);
		return getOrderedList(params);
	}
	
}
