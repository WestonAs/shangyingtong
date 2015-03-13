package gnete.card.entity.flag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

public class AdjAccFlag extends AbstractType{

	public static final Map ALL = new HashMap();
	
	public static final AdjAccFlag ADJ_ACC = new AdjAccFlag("调账", "00");
	public static final AdjAccFlag RETURN_GOODS = new AdjAccFlag("退货", "01");
	
	protected AdjAccFlag(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static AdjAccFlag valueOf(String value) {
		AdjAccFlag type = (AdjAccFlag) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的标志");
		}
		return type;
	}
	
	public static List getAll(){
		return getOrderedList(AdjAccFlag.ALL);
	}

}
