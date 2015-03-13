package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;
/**
 * <li>微信订单商户类别</li>
 * @Project: cardWx
 * @File: WxBillType.java
 *
 * @copyright: (c) 2014 ITECH INC.
 * @author: zhanghl
 * @modify:
 * @version: 1.0
 * @since 1.0 2014-4-18 下午03:26:34
 */
public class WxBillType extends AbstractType {

	public static final Map<String, WxBillType> ALL = new HashMap<String, WxBillType>();
	
	public static final WxBillType PERSONAL = new WxBillType("个人", "P");
	public static final WxBillType BUSINESS = new WxBillType("商户", "E");
	
	protected WxBillType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static WxBillType valueOf(String value) {
		WxBillType type = (WxBillType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("订单类型错误");
		}
		return type;
	}
	
	@SuppressWarnings("unchecked")
	public static List<WxBillType> getAll(){
		return getValueOrderedList(WxBillType.ALL);
	}
}
