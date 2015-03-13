package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * <li>实名卡扣款充值对账明细差错类型</li>
 * 
 * @File: WxReconOpeType.java
 * 
 * @copyright: (c) 2014 ITECH INC.
 * @author: ZhaoWei
 * @modify:
 * @version: 1.0
 * @since 1.0 2014-4-17 下午06:31:49
 */
public class WxReconErrorType extends AbstractType {

	public static final Map<String, WxReconErrorType> ALL = new HashMap<String, WxReconErrorType>();

	/**
	 * 差错类型
	 * 0 长款
	 * 1 短款
	 * 2 差异
	 */

	public static final WxReconErrorType LONG = new WxReconErrorType("长款", "0");
	public static final WxReconErrorType LOW = new WxReconErrorType("短款", "1");
	public static final WxReconErrorType DIFF = new WxReconErrorType("差异", "2");

	protected WxReconErrorType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static WxReconErrorType valueOf(String value) {
		WxReconErrorType type = (WxReconErrorType) ALL.get(value);

		if (type == null) {
			throw new RuntimeBizException("不正确的类型");
		}

		return type;
	}

	@SuppressWarnings("unchecked")
	public static List<WxReconErrorType> getAll() {
		return getOrderedList(WxReconErrorType.ALL);
	}
}
