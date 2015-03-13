package gnete.card.entity.state;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

/**
 * @File:GiftDefState.java
 *
 * @description: 卡状态
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-lib
 * @version: 1.0
 * @since 1.0 2010-8-3
 */
public class CardState extends AbstractState{
	
	public static final Map ALL = new LinkedHashMap();
	public static final Map PRE = new HashMap();

	public static final CardState WAITED = new CardState("待制卡", "00");
	public static final CardState MADED = new CardState("已制卡", "02");	
	public static final CardState STOCKED= new CardState("已入库", "03");
	public static final CardState FORSALE = new CardState("已领卡待售", "04");
	public static final CardState PRESELLED = new CardState("预售出", "05");
	public static final CardState ACTIVE = new CardState("正常(已激活)", "10");
	public static final CardState EXCEEDED = new CardState("已过期", "20");
	public static final CardState CLOSEACC = new CardState("已退卡(销户)", "21");
	public static final CardState LOSSREGISTE = new CardState("挂失", "22");
	public static final CardState LOSSREPLACE = new CardState("挂失已补卡", "23");
	public static final CardState POSTPONED = new CardState("已延期", "24");
	public static final CardState RECYCLE = new CardState("已回收", "26");
	public static final CardState LOCKED = new CardState("锁定", "27");
	public static final CardState IC_RECYCLE = new CardState("IC卡收回", "28");
	public static final CardState IC_LOSSCARD= new CardState("IC卡挂失", "29");
	
	@SuppressWarnings("unchecked")
	protected CardState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static CardState valueOf(String value) {
		CardState type = (CardState) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("流程状态错误！");
		}
		return type;
	}
	
	public static List<CardState> getAll(){
		return new ArrayList<CardState>(CardState.ALL.values());
	}
	
	public static List getPreCard(){
		Map params = new HashMap();
		params.put(WAITED.getValue(), WAITED);
		params.put(MADED.getValue(), MADED);
		params.put(STOCKED.getValue(), STOCKED);
		params.put(FORSALE.getValue(), FORSALE);
		return getOrderedList(params);
	}
	
	public static List getExpireCard(){
		Map params = new HashMap();
		params.put(EXCEEDED.getValue(), EXCEEDED);
		params.put(CLOSEACC.getValue(), CLOSEACC);
		params.put(RECYCLE.getValue(), RECYCLE);
		
		return getOrderedList(params);
	}
	
	public static List getCouponCard(){
		Map params = new HashMap();
		params.put(FORSALE.getValue(), FORSALE);
		
		return getOrderedList(params);
	}
	
	public static List getActiveStatus() {
		Map params = new HashMap();
		params.put(ACTIVE.getValue(), ACTIVE);
		params.put(EXCEEDED.getValue(), EXCEEDED);
		params.put(CLOSEACC.getValue(), CLOSEACC);
		params.put(LOSSREGISTE.getValue(), LOSSREGISTE);
		params.put(LOSSREPLACE.getValue(), LOSSREPLACE);
		params.put(POSTPONED.getValue(), POSTPONED);
		params.put(RECYCLE.getValue(), RECYCLE);
		
		return getOrderedList(params);
	}
}
