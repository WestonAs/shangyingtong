package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * @File: IcRenewCardType.java
 *
 * @description: IC卡换卡类型
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @modify:
 * @version: 1.0
 * @since 1.0 2012-9-24 上午11:31:24
 */
public class IcRenewCardType extends AbstractType {

	
	public static final Map ALL = new HashMap();
	
	/** 可读卡换卡 */
	public static final IcRenewCardType CAN_READ_CARD = new IcRenewCardType("可读卡换卡","0");
	/** 不可读卡换卡 */
	public static final IcRenewCardType CANT_READ_CARD = new IcRenewCardType("不可读卡换卡","1");
	/** 挂失换卡 */
	public static final IcRenewCardType LOSS_CARD = new IcRenewCardType("挂失换卡","2");
	
	@SuppressWarnings("unchecked")
	protected IcRenewCardType(String name, String value) {
		
		super(name, value);
		ALL.put(value, this);
	}

	public static IcRenewCardType valueOf(String value) {
		IcRenewCardType type = (IcRenewCardType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的类型");
		}
		return type;
	}
	
	public static List getAll(){
		return getValueOrderedList(IcRenewCardType.ALL);
	}
	
	/**
	 *  IC卡不可读卡的换卡类型
	 * @return
	 */
	public static List getUnReadTypeList() {
		Map params = new HashMap();
		
		params.put(CANT_READ_CARD.getValue(), CANT_READ_CARD);
		params.put(LOSS_CARD.getValue(), LOSS_CARD);
		
		return getValueOrderedList(params);
	}
}
