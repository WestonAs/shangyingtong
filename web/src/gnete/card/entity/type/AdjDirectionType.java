package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * @File: AdjDirectionType.java
 *
 * @description: 调整方向类型
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-lib
 * @version: 1.0
 * @since 1.0 2010-9-7
 */
public class AdjDirectionType extends AbstractType {

	public static final Map ALL = new HashMap();
	
	/**
	 * 准备金调整方向类型
	 */
	public static final AdjDirectionType INCREASE = new AdjDirectionType("增加","00");
	public static final AdjDirectionType DECREASE = new AdjDirectionType("减少","10");
	
	protected AdjDirectionType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static AdjDirectionType valueOf(String value) {
		AdjDirectionType type = (AdjDirectionType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的类型");
		}
		return type;
	}
	
	public static List getAll(){
		return getOrderedList(AdjDirectionType.ALL);
	}

}
