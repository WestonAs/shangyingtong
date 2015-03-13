package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * @author aps-bey
 */
public class DerateType extends AbstractType {
	public static final Map ALL = new HashMap();

	
	/*
	 * 年费减免方式：
		0-按次减免
		1-按金额减免
		2-不收费
	*/	
	public static final DerateType COUNT  = new DerateType("按次减免", "0");
	public static final DerateType SUMAMT = new DerateType("按金额减免", "1");
	public static final DerateType FREE = new DerateType("不收费", "2");
	
	@SuppressWarnings("unchecked")
	protected DerateType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static DerateType valueOf(String value) {
		DerateType type = (DerateType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的类型");
		}

		return type;
	}
	
	public static List getAll(){
		return getOrderedList(DerateType.ALL);
	}
	
}
