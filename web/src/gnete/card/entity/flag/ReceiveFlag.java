package gnete.card.entity.flag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * @File: ReceiveFlag.java
 *
 * @description: 发生方式：领卡/返库
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-zwi
 * @version: 1.0
 * @since 1.0 2010-8-24
 */
public class ReceiveFlag extends AbstractType{
	
	public static final Map ALL = new HashMap();
	
	/*
	 	0-领卡
		1-返库
	*/
	public static final ReceiveFlag RECEIVE = new ReceiveFlag("领卡", "0");
	public static final ReceiveFlag WITHDRAW = new ReceiveFlag("返库", "1");
	
	@SuppressWarnings("unchecked")
	protected ReceiveFlag(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static ReceiveFlag valueOf(String value) {
		ReceiveFlag type = (ReceiveFlag) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的类型");
		}
		return type;
	}
	
	public static List getAll(){
		return getOrderedList(ReceiveFlag.ALL);
	}
	
}
