package gnete.card.entity.type;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;
/**
 * <li>微信用户信息类别</li>
 * @Project: cardWx
 * @File: WxUserType.java
 *
 * @copyright: (c) 2014 ITECH INC.
 * @author: zhanghl
 * @modify:
 * @version: 1.0
 * @since 1.0 2014-4-18 下午03:26:34
 */
public class WxUserType extends AbstractState {

	public static final Map<String, WxUserType> ALL = new LinkedHashMap<String, WxUserType>();
	
	/**
	 * 
	 * @param name
	 * @param value
	 * P0: 个人用户
	 * P1：个体户
	 * MX：商户(M0)
	 * IX：机构(I0)
	 * (X：暂定为0)
	 */
	
	public static final WxUserType PERSONAL = new WxUserType("个人用户", "P0");
	
	public static final WxUserType SALESER = new WxUserType("个体户", "P1");
	
	public static final WxUserType BUSINESS = new WxUserType("商户", "MX");
	
	public static final WxUserType DEPT = new WxUserType("机构", "IX");
	
	protected WxUserType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static WxUserType valueOf(String value) {
		WxUserType type = (WxUserType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("用户类型错误");
		}
		return type;
	}
	
	@SuppressWarnings("unchecked")
	public static List<WxUserType> getAll(){
		return getValueOrderedList(WxUserType.ALL);
	}
}
