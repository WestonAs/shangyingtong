package gnete.card.entity.type;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;
/**
 * <li>微信收银员权限类别</li>
 * @Project: cardWx
 * @File: WxCashierPermisionType.java
 *
 * @copyright: (c) 2014 ITECH INC.
 * @author: zhanghl
 * @modify:
 * @version: 1.0
 * @since 1.0 2014-4-18 下午03:26:34
 */
public class WxCashierPermisionType extends AbstractState {

	public static final Map<String, WxCashierPermisionType> ALL = new LinkedHashMap<String, WxCashierPermisionType>();
	
	/**
	 * 
	 * @param name
	 * @param value
	 * 000000:只能收款
	 * 100000:表示有退款权限
	 * 110000:待定
	 */
	
	public static final WxCashierPermisionType RECEIVE = new WxCashierPermisionType("只能收款", "000000");
	
	public static final WxCashierPermisionType PAYBACK = new WxCashierPermisionType("可以退款", "100000");
	
	public static final WxCashierPermisionType WAIT = new WxCashierPermisionType("可以退款", "110000");
	
	protected WxCashierPermisionType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static WxCashierPermisionType valueOf(String value) {
		WxCashierPermisionType type = (WxCashierPermisionType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("收银员收款权限错误！");
		}
		return type;
	}
	
	@SuppressWarnings("unchecked")
	public static List<WxCashierPermisionType> getAll(){
		return getOrderedList(ALL);
	}
}
