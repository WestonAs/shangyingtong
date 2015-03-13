package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

public class AmtCntType extends AbstractType{

	public static final Map ALL = new HashMap();
	
	public static final AmtCntType AMT = new AmtCntType("按金额计费","0");
	public static final AmtCntType CNT = new AmtCntType("按笔数计费","1");
	
	protected AmtCntType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static AmtCntType valueOf(String value) {
		AmtCntType type = (AmtCntType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的类型");
		}
		return type;
	}
	
	public static List getAll(){
		return getOrderedList(AmtCntType.ALL);
	}

}
