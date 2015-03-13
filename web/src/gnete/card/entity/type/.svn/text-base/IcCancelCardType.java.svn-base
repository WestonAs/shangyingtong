package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * @File: IcCancelCardType.java
 *
 * @description: IC卡销卡类型
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @modify:
 * @version: 1.0
 * @since 1.0 2012-10-09 上午11:31:24
 */
public class IcCancelCardType extends AbstractType {

	
	public static final Map ALL = new HashMap();
	
	/** 可读卡销卡 */
	public static final IcCancelCardType CAN_READ_CARD = new IcCancelCardType("可读卡销卡","0");
	/** 不可读卡销卡 */
	public static final IcCancelCardType CANT_READ_CARD = new IcCancelCardType("不可读卡销卡","1");
	/** 挂失销卡 */
	public static final IcCancelCardType LOSS_CARD = new IcCancelCardType("挂失销卡","2");
	
	@SuppressWarnings("unchecked")
	protected IcCancelCardType(String name, String value) {
		
		super(name, value);
		ALL.put(value, this);
	}

	public static IcCancelCardType valueOf(String value) {
		IcCancelCardType type = (IcCancelCardType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的类型");
		}
		return type;
	}
	
	public static List getAll(){
		return getValueOrderedList(IcCancelCardType.ALL);
	}
	
	/**
	 *  IC卡不可读卡的销卡类型
	 * @return
	 */
	public static List getUnReadTypeList() {
		Map params = new HashMap();
		
		params.put(CANT_READ_CARD.getValue(), CANT_READ_CARD);
		params.put(LOSS_CARD.getValue(), LOSS_CARD);
		
		return getValueOrderedList(params);
	}
}
