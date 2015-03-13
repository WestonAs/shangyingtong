package gnete.card.entity.flag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * 商圈积分赠券权限表 :  消费标志
 * @Project: CardWeb
 * @File: ConsumeFlag.java
 * @See:
 * @author: aps-qfg
 * @modified:
 * @Email: aps-qfg@cnaps.com.cn
 * @Date: 2013-2-25上午9:51:09
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2013 版权所有
 * @version: V1.0
 */
public class ConsumeFlag extends AbstractType{

	public static final Map ALL = new HashMap();
	
	public static final ConsumeFlag CONSUMNE_Y = new ConsumeFlag("可消费", "0");
	public static final ConsumeFlag CONSUMNE_N = new ConsumeFlag("不可消费", "1");
	
	protected ConsumeFlag(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static ConsumeFlag valueOf(String value) {
		ConsumeFlag type = (ConsumeFlag) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的标志");
		}
		return type;
	}
	
	public static List getAll(){
		return getOrderedList(ConsumeFlag.ALL);
	}

}
