package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * 发卡机构手续费计费原则
 * @author aps-lib
 */
public class FeePrincipleType extends AbstractType {
	public static final Map ALL = new HashMap();
	
	/*
	0: 按计费周期实际费率
	1: 按计费周期最小费率计费
	3: 按调整周期实际费率
	*/
	
	public static final FeePrincipleType TRADE_NUM = new FeePrincipleType("按计费周期实际费率", "0");
	public static final FeePrincipleType TRADE_NUM_STAGE = new FeePrincipleType("按计费周期最小费率计费", "1");
	public static final FeePrincipleType TRADE_NUM_STAGE_CUM = new FeePrincipleType("按调整周期实际费率", "3");
	
	public static FeePrincipleType valueOf(String value) {
		FeePrincipleType type = (FeePrincipleType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("没有该计费原则");
		}

		return type;
	}
	
	@SuppressWarnings("unchecked")
	protected FeePrincipleType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static List getList() {
		return getOrderedList(ALL);
	}
	
	/**
	 * 发卡机构手续费计费方式为交易笔数、按金额固定比例保底封顶
	 * 对应的计费原则
	 * @return
	 */
	public static List getTradeNum(){
		Map params = new HashMap();
		params.put(TRADE_NUM.getValue(), TRADE_NUM);
		return getOrderedList(params);
	}
	
}
