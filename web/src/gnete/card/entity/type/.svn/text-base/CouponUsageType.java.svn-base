package gnete.card.entity.type;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CouponUsageType extends AbstractType{
	public static final Map ALL = new HashMap();
	
	/*
	赠券使用方法:
		0：常用 （一般消费）
		1：满M元(见金额参数1)使用0~N元（见金额参数2）
		2：每满M元(见金额参数1)使用0~N元（见金额参数2）
		3：按应扣金额M比例K（M*K）使用（K见金额参数2）
		：其他保留
	*/
	public static final CouponUsageType COMMONUSE = new CouponUsageType("常用", "0");
	public static final CouponUsageType MNUSE = new CouponUsageType("满M元使用0~N元", "1");
	public static final CouponUsageType LUSE = new CouponUsageType("每满M元使用0~N元", "2");
	public static final CouponUsageType MKUSE = new CouponUsageType("按应扣金额M比例K（M*K）使用", "3");
	
	@SuppressWarnings("unchecked")
	protected CouponUsageType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static CouponUsageType valueOf(String value) {
		CouponUsageType type = (CouponUsageType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的类型");
		}
		return type;
	}
	
	public static List getAll(){
		return getOrderedList(CouponUsageType.ALL);
	}
	
	
}
