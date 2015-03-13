package gnete.card.entity.state;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

/**
 * @File: CardStockState.java
 *
 * @description: 卡库存状态
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-zwi
 * @version: 1.0
 * @since 1.0 2010-8-24
 */
public class CardStockState extends AbstractState {
	public static final Map ALL = new LinkedHashMap();

	/** 未入库 */
	public static final CardStockState UN_STOCK = new CardStockState("未入库", "02");
	/** 卡在途 */
	public static final CardStockState ON_THE_WAY = new CardStockState("卡在途", "01");
	/** 卡在库 */
	public static final CardStockState IN_STOCK = new CardStockState("卡在库", "00");
	/** 已领卡 */
	public static final CardStockState RECEIVED = new CardStockState("已领卡", "10");
	/** 售卡中 */
	public static final CardStockState SOLD_ING = new CardStockState("售卡中", "50");	
	/** 已派出 */
	public static final CardStockState SEND_OUT = new CardStockState("已派出", "30");	
	/** 预售出 */
	public static final CardStockState PRE_SOLD = new CardStockState("预售出", "40");	
	/** 已售出 */
	public static final CardStockState SOLD_OUT = new CardStockState("已售出", "20");	
	
	@SuppressWarnings("unchecked")
	protected CardStockState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static CardStockState valueOf(String value) {
		CardStockState type = (CardStockState) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("错误的状态！");
		}

		return type;
	}
	
	public static List getAll(){
		return new ArrayList(CardStockState.ALL.values());
	}
	
	/**
	 * 取得可领卡的状态
	 * @return
	 */
	public static List getReceiveList() {
		Map params = new HashMap();
		params.put(IN_STOCK.getValue(), IN_STOCK);
		params.put(RECEIVED.getValue(), RECEIVED);
		
		return getOrderedList(params);
	}
}
