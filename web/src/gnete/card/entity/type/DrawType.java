package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * @File: DrawType.java
 *
 * @description: 开奖方式
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-zwi
 * @version: 1.0
 * @since 1.0 2010-8-17
 */
public class DrawType extends AbstractType{
	
	public static final Map ALL = new HashMap();
	
	/*
	 	0：现场开奖
		1：即刷即中
	*/
	public static final DrawType DRAW_LIVE = new DrawType("现场开奖", "0");
	public static final DrawType BRUSH_ISIN = new DrawType("即刷即中", "1");
	
	@SuppressWarnings("unchecked")
	protected DrawType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static DrawType valueOf(String value) {
		DrawType type = (DrawType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的开奖方式");
		}
		return type;
	}
	
	public static List getAll(){
		return getOrderedList(DrawType.ALL);
	}
	
}
