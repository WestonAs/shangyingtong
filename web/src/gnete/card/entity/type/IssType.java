package gnete.card.entity.type;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @File: IssType.java
 *
 * @description: 机构类型代码
 * 
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-zwi
 * @version: 1.0
 * @since 1.0 2010-8-17
 */
public class IssType extends AbstractType{
	public static final Map ALL = new HashMap();
	
	/*
	机构类型代码:
		0-发卡机构
		1-商户
		2-商圈
	*/
	public static final IssType CARD = new IssType("发卡机构", "0");
	public static final IssType MERCHANT = new IssType("商户", "1");
	public static final IssType CIRCLE = new IssType("商圈", "2");
	
	@SuppressWarnings("unchecked")
	protected IssType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static IssType valueOf(String value) {
		IssType type = (IssType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的类型");
		}
		return type;
	}
	
	public static List getAll(){
		return getOrderedList(IssType.ALL);
	}
	
	public static List getCardDefineType(){
		Map params = new HashMap();
		params.put(CARD.getValue(), CARD);
		return getOrderedList(params);
	}
	
	public static List getCardMerchType(){
		Map params = new HashMap();
		params.put(CARD.getValue(), CARD);
		params.put(MERCHANT.getValue(), MERCHANT);
		return getOrderedList(params);
	}
	
	public static List getCircleDefineType(){
		Map params = new HashMap();
		params.put(CARD.getValue(), CARD);
		params.put(CIRCLE.getValue(), CIRCLE);
		return getOrderedList(params);
	}
	
}
