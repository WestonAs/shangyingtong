package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * @File: AdjType.java
 *
 * @description: 风险准备金调整类型
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-lib
 * @version: 1.0
 * @since 1.0 2010-9-29
 */
public class AdjType extends AbstractType{

	public static final Map ALL = new HashMap();
	
	/**
	 * 准备金调整类型
	 */
	public static final AdjType APTITUDE = new AdjType("资质额度调整","0");
	public static final AdjType DEPOSIT = new AdjType("存入风险保证金","1");
	public static final AdjType DRAW = new AdjType("风险保证提现","2");
	public static final AdjType SELL = new AdjType("售卡","3");
	public static final AdjType MANAGE = new AdjType("管理充值","4");
	public static final AdjType SYSTEM = new AdjType("系统充值","5");
	public static final AdjType SETTLE = new AdjType("清算资金","6");
	public static final AdjType COUPON = new AdjType("派发通用赠券","8");
	
	protected AdjType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static AdjType valueOf(String value) {
		AdjType type = (AdjType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的类型");
		}
		return type;
	}
	
	public static List getAll(){
		return getOrderedList(AdjType.ALL);
	}
	
	public static List getCardRiskRegType(){
		Map params = new HashMap();
		params.put(APTITUDE.getValue(), APTITUDE);
		params.put(DEPOSIT.getValue(), DEPOSIT);
		params.put(DRAW.getValue(), DRAW);
		
		return getOrderedList(params);
	}
	
	public static List getSellAmtRegType(){
		Map params = new HashMap();
//		params.put(APTITUDE.getValue(), APTITUDE);
		params.put(DEPOSIT.getValue(), DEPOSIT);
		params.put(DRAW.getValue(), DRAW);
		
		return getOrderedList(params);
	}

}
