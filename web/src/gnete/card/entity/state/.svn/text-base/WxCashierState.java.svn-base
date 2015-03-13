package gnete.card.entity.state;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;
/**
 * <li>微信收银员查询状态</li>
 * @Project: cardWx
 * @File: WxCashierState.java
 *
 * @copyright: (c) 2014 ITECH INC.
 * @author: zhanghl
 * @modify:
 * @version: 1.0
 * @since 1.0 2014-4-18 下午03:26:34
 */
public class WxCashierState extends AbstractState {

	public static final Map<String, WxCashierState> ALL = new LinkedHashMap<String, WxCashierState>();
	
	/**
	 * 
	 * @param name
	 * @param value
	 * 1.生效
	 * 0.解除
	 */
	
	public static final WxCashierState EFECTIVE = new WxCashierState("生效", "1");
	
	public static final WxCashierState UNEFECTIVE = new WxCashierState("解除", "0");
	
	protected WxCashierState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static WxCashierState valueOf(String value) {
		WxCashierState type = (WxCashierState) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("收银员状态错误！");
		}
		return type;
	}

	@SuppressWarnings("unchecked")
	public static List<WxCashierState> getAll(){
		return getOrderedList(ALL);
	}
}
