package gnete.card.entity.type;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * @description 风险级别
 */
public class CardMembFeeFeeType extends AbstractType{
	
	public static final Map ALL = new LinkedHashMap();
	
	/** 0 表示低 */
	public static final CardMembFeeFeeType TRADE_NUM = new CardMembFeeFeeType("按交易笔数固定金额", "0");
	/** 1 表示中 */
	public static final CardMembFeeFeeType MONEY_RATE_MAX_MIN = new CardMembFeeFeeType("按交易金额固定比率单笔保底封顶", "1");
	
	
	protected CardMembFeeFeeType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static CardMembFeeFeeType valueOf(String value) {
		CardMembFeeFeeType type = (CardMembFeeFeeType) ALL.get(value);
		if (type == null) {
			throw new RuntimeBizException("不正确的类型");
		}
		return type;
	}
	
	public static List getAll(){
		return getValueOrderedList(CardMembFeeFeeType.ALL);
	}
}
