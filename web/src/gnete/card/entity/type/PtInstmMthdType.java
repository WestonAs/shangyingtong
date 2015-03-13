package gnete.card.entity.type;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PtInstmMthdType extends AbstractType{
	public static final Map ALL = new HashMap();
	
	/*
	积分分期方法:
		1：自然周期 N月后月初为周期切换日
		2：使用周期：使用满N月后启用日期为周期切换日
	*/
	public static final PtInstmMthdType NATURALCYCLE = new PtInstmMthdType("自然周期", "1");
	public static final PtInstmMthdType USECYCLE = new PtInstmMthdType("使用周期", "2");
	
	@SuppressWarnings("unchecked")
	protected PtInstmMthdType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static PtInstmMthdType valueOf(String value) {
		PtInstmMthdType type = (PtInstmMthdType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的类型");
		}
		return type;
	}
	
	public static List getAll(){
		return getOrderedList(PtInstmMthdType.ALL);
	}
	
	
}
