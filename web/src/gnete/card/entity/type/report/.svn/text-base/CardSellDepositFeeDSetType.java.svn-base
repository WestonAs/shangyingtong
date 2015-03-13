package gnete.card.entity.type.report;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * @File: CardSellDepositDSetType.java
 *
 * @description: 发卡机构角色下的售卡充值日结算报表
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2011-6-21
 */
public class CardSellDepositFeeDSetType extends AbstractType{
	
	public static final Map ALL = new HashMap();
	
	public static final CardSellDepositFeeDSetType SELL_DEPOSIT_BRANCH = new CardSellDepositFeeDSetType("Web售卡充值手续费日报表(机构)", "0");
	public static final CardSellDepositFeeDSetType SELL_DEPOSIT_CUSTOMER = new CardSellDepositFeeDSetType("Web售卡充值手续费日报表(购卡客户)", "1");
	
	@SuppressWarnings("unchecked")
	protected CardSellDepositFeeDSetType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static CardSellDepositFeeDSetType valueOf(String value) {
		CardSellDepositFeeDSetType type = (CardSellDepositFeeDSetType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的卡类型");
		}
		return type;
	}
	
	public static List getAll(){
		return getOrderedList(CardSellDepositFeeDSetType.ALL);
	}
}
