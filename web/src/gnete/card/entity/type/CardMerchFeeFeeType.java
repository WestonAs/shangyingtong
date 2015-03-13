package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * @author aps-mjn
 */
public class CardMerchFeeFeeType extends AbstractType {
	public static final Map ALL = new HashMap();
	
	/*
	分支机构运营管理手续费
	*/
	public static final CardMerchFeeFeeType EACH = new CardMerchFeeFeeType("按交易笔数","0");
	public static final CardMerchFeeFeeType FEE = new CardMerchFeeFeeType("金额固定比例", "1");
	public static final CardMerchFeeFeeType TRADEMONEY = new CardMerchFeeFeeType("金额分段比例", "2");
	public static final CardMerchFeeFeeType SSUM = new CardMerchFeeFeeType("金额段阶梯收费","3");
	
	public static CardMerchFeeFeeType valueOf(String value) {
		CardMerchFeeFeeType type = (CardMerchFeeFeeType) ALL.get(value);
		if (type == null) {
			throw new RuntimeBizException("没有该计费方式");
		}
		return type;
	}
	
	@SuppressWarnings("unchecked")
	protected CardMerchFeeFeeType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static List getList() {
		return getOrderedList(ALL);
	}
	
	public static List getEach(){
		Map params = new HashMap();
		params.put(EACH.getValue(), EACH);
		return getOrderedList(params);
	}
	
}
