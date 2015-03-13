package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * 商圈积分赠券权限表 : 限制类型
 * @Project: CardWeb
 * @File: LimitType.java
 * @See:
 * @author: aps-qfg
 * @modified:
 * @Email: aps-qfg@cnaps.com.cn
 * @Date: 2013-2-25上午9:50:01
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2013 版权所有
 * @version: V1.0
 */
public class LimitType extends AbstractType{

	public static final Map ALL = new HashMap();
	
	public static final LimitType POINT = new LimitType("积分", "0");
	public static final LimitType COUPON = new LimitType("赠券", "1");
	
	protected LimitType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static LimitType valueOf(String value) {
		LimitType type = (LimitType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的标志");
		}
		return type;
	}
	
	public static List getAll(){
		return getOrderedList(LimitType.ALL);
	}

}
