package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

public class TrailType extends AbstractType{
	public static final Map ALL = new HashMap();
	
	/*
	试算平衡类型:
		00- 全部
		01-资金账户
		02-次卡账户
		03-联名赠券账户
		04-积分账户
	*/

	public static final TrailType ALL_ACCT = new TrailType("全部", "00");
	public static final TrailType CAPITAL_ACCT = new TrailType("资金帐户", "01");
	public static final TrailType TIME_CARD_ACCT = new TrailType("次卡帐户", "02");
	public static final TrailType COUPON_ACCT = new TrailType("联名赠券帐户", "03");
	public static final TrailType POINT_ACCT = new TrailType("积分帐户", "04");
	
	protected TrailType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static TrailType valueOf(String value) {
		TrailType type = (TrailType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的类型");
		}
		return type;
	}
	
	public static List getAll(){
		return getOrderedList(TrailType.ALL);
	}
	
	public static List getMerchType() {
		Map params = new HashMap();
		params.put(POINT_ACCT.getValue(), POINT_ACCT);
		params.put(COUPON_ACCT.getValue(), COUPON_ACCT);
		
		return getOrderedList(params);
	}

}
