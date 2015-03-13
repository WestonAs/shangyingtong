package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * @File: VerifyType.java
 *
 * @description: 核销类型 1 - 等额核销 2 - 差额核销 3 - 部分核销
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2010-11-11
 */
public class VerifyType extends AbstractType{
	
	public static final Map ALL = new HashMap();
	
	/*
	 	1 - 等额核销
		2 - 差额核销
		3 - 部分核销
	*/
	public static final VerifyType EQUA = new VerifyType("等额核销", "1");
	public static final VerifyType DIFF = new VerifyType("差额核销", "2");
	public static final VerifyType PART = new VerifyType("部分核销", "3");
	
	@SuppressWarnings("unchecked")
	protected VerifyType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static VerifyType valueOf(String value) {
		VerifyType type = (VerifyType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的核销类型");
		}
		return type;
	}
	
	public static List getAll(){
		return getOrderedList(VerifyType.ALL);
	}
	
}
