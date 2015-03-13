package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * @File: DiscntOperMethod.java
 *
 * @description: 折扣操作方法
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-zwi
 * @version: 1.0
 * @since 1.0 2010-10-10
 */
public class DiscntOperMethod extends AbstractType {

	
	public static final Map ALL = new HashMap();
	
	/**
	 *  0：操作员人工操作
		1：系统自动折扣（应扣款折扣）
		2：系统自动折扣（分成折扣）
	 */
	public static final DiscntOperMethod OPERATOR_MANUAL = new DiscntOperMethod("操作员人工操作","0");
	public static final DiscntOperMethod SYSTEM_CHARGED = new DiscntOperMethod("系统自动折扣（应扣款折扣）","1");
	public static final DiscntOperMethod SYSTEM_INTO = new DiscntOperMethod("系统自动折扣（分成折扣）","2");
	
	@SuppressWarnings("unchecked")
	protected DiscntOperMethod(String name, String value) {
		
		super(name, value);
		ALL.put(value, this);
	}

	public static DiscntOperMethod valueOf(String value) {
		DiscntOperMethod cert = (DiscntOperMethod) ALL.get(value);
		
		if (cert == null) {
			throw new RuntimeBizException("不正确的类型");
		}
		return cert;
	}
	
	public static List getAll(){
		return getOrderedList(DiscntOperMethod.ALL);
	}
	
	public static List getManualList() {
		Map params = new HashMap();
		params.put(OPERATOR_MANUAL.getValue(), OPERATOR_MANUAL);
		return getOrderedList(params);
	}
}
