package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * @File: ProbType.java
 *
 * @description: 概率折算方法
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-zwi
 * @version: 1.0
 * @since 1.0 2010-8-17
 */
public class ProbType extends AbstractType{
	
	public static final Map ALL = new HashMap();
	
	/*
	 	0：固定倍数（最小倍数）
		1：大等于基准金额后固定倍数
		2：金额按参考金额倍
		3：2 + 1倍
	*/
	public static final ProbType NORMAL = new ProbType("0、固定倍数（最小倍数）", "0");
	public static final ProbType GREAT = new ProbType("1、大等于基准金额后固定倍数", "1");
	public static final ProbType REFER = new ProbType("2、金额按参考金额倍", "2");
	public static final ProbType OTHER = new ProbType("3、2 + 1倍", "3");
	
	@SuppressWarnings("unchecked")
	protected ProbType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static ProbType valueOf(String value) {
		ProbType type = (ProbType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的概率折算方法");
		}
		return type;
	}
	
	public static List getAll(){
		return getOrderedList(ProbType.ALL);
	}
	
}
