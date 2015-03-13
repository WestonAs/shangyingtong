package gnete.card.entity.type.report;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * @File: CardTransDSetType.java
 *
 * @description: 发卡机构角色下的交易日结算报表
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2011-6-21
 */
public class CardTransDSetType extends AbstractType{
	
	public static final Map ALL = new HashMap();
	
	/*
	 	0-积分卡业务商户交易划账汇总报表
		1-交易清算日结算报表
	*/
	public static final CardTransDSetType MERCH_TRANS_RMA = new CardTransDSetType("商户交易划账汇总报表", "0");
	public static final CardTransDSetType TRANS_DSET = new CardTransDSetType("交易清算日结算报表", "1");
	
	@SuppressWarnings("unchecked")
	protected CardTransDSetType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static CardTransDSetType valueOf(String value) {
		CardTransDSetType type = (CardTransDSetType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的卡类型");
		}
		return type;
	}
	
	public static List getAll(){
		return getOrderedList(CardTransDSetType.ALL);
	}
	
}
