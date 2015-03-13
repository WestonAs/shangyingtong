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
public class CardSellDepositDSetType extends AbstractType{
	
	public static final Map ALL = new HashMap();
	
	/*
	 	0-积分卡业务售卡充值日结算报表
		1-积分卡业务充值售卡对账汇总报表
		2-积分卡业务充值售卡对账明细报表
	*/
	public static final CardSellDepositDSetType SELL_DEPOSIT_DSET = new CardSellDepositDSetType("售卡充值日结算报表", "0");
	public static final CardSellDepositDSetType TRANS_VOA_DSUM = new CardSellDepositDSetType("充值售卡对账汇总报表", "1");
	public static final CardSellDepositDSetType TRANS_VOA_DDETAIL = new CardSellDepositDSetType("充值售卡对账明细报表", "2");
	
	@SuppressWarnings("unchecked")
	protected CardSellDepositDSetType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static CardSellDepositDSetType valueOf(String value) {
		CardSellDepositDSetType type = (CardSellDepositDSetType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的卡类型");
		}
		return type;
	}
	
	public static List getAll(){
		return getOrderedList(CardSellDepositDSetType.ALL);
	}
}
