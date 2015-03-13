package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * <li>实名卡扣款充值对账处理登记簿操作类型</li>
 * 
 * @File: WxReconOpeType.java
 * 
 * @copyright: (c) 2014 ITECH INC.
 * @author: ZhaoWei
 * @modify:
 * @version: 1.0
 * @since 1.0 2014-4-17 下午06:31:49
 */
public class WxReconOpeType extends AbstractType {

	public static final Map<String, WxReconOpeType> ALL = new HashMap<String, WxReconOpeType>();

	/**
	 * 操作的类型: 
	 * 0补帐 
	 * 1调账
	 */

	public static final WxReconOpeType FILL = new WxReconOpeType("补帐", "0");
	public static final WxReconOpeType FIT = new WxReconOpeType("调账", "1");

	protected WxReconOpeType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static WxReconOpeType valueOf(String value) {
		WxReconOpeType type = (WxReconOpeType) ALL.get(value);

		if (type == null) {
			throw new RuntimeBizException("不正确的类型");
		}

		return type;
	}

	@SuppressWarnings("unchecked")
	public static List<WxReconOpeType> getAll() {
		return getOrderedList(WxReconOpeType.ALL);
	}
}
