package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

public class ChlFeeContentType extends AbstractType {

	public static final Map ALL = new HashMap();
	
	public static final ChlFeeContentType PREPAY = new ChlFeeContentType("预付资金", "1");
	public static final ChlFeeContentType POINT = new ChlFeeContentType("通用积分资金", "2");
	public static final ChlFeeContentType COUPON = new ChlFeeContentType("赠券资金","3");
	public static final ChlFeeContentType SPECIAL_POINT_TRANS_NUM = new ChlFeeContentType("专属积分交易笔数","4");
	public static final ChlFeeContentType TIME_CARD = new ChlFeeContentType("次卡可用次数","5");
	public static final ChlFeeContentType TIME_CARD_MEMB = new ChlFeeContentType("会员数","6");
	public static final ChlFeeContentType DISCOUNT_CARD_MEMB = new ChlFeeContentType("折扣卡会员数","7");
	
	protected ChlFeeContentType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static List getList() {
		return getOrderedList(ALL);
	}
	
	public static ChlFeeContentType valueOf(String value) {
		ChlFeeContentType type = (ChlFeeContentType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("没有该类型");
		}

		return type;
	}

}
