package gnete.card.entity.type;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 是否批量处理
 * @Project: CardWeb
 * @File: SingleBatchType.java
 * @See:
 * @author: aps-qfg
 * @modified:
 * @Email: aps-qfg@cnaps.com.cn
 * @Date: 2013-1-21下午5:42:12
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2012 版权所有
 * @version: V1.0
 */
public class SingleBatchType extends AbstractType{
	public static final Map ALL = new HashMap();
	
	public static final SingleBatchType SINGLE = new SingleBatchType("否", "01");
	public static final SingleBatchType BATCH = new SingleBatchType("是", "02");
	
	public static final SingleBatchType SINGLE_SHORT = new SingleBatchType("否", "0");
	public static final SingleBatchType BATCH_SHORT = new SingleBatchType("是", "1");
	
	@SuppressWarnings("unchecked")
	protected SingleBatchType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static SingleBatchType valueOf(String value) {
		SingleBatchType type = (SingleBatchType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的类型");
		}
		return type;
	}
	
	public static List getAll(){
		return getOrderedList(SingleBatchType.ALL);
	}
	
}
