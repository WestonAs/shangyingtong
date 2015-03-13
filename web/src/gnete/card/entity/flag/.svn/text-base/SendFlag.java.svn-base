package gnete.card.entity.flag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * 商圈积分赠券权限表 : 赠送标志
 * @Project: CardWeb
 * @File: SendFlag.java
 * @See:
 * @author: aps-qfg
 * @modified:
 * @Email: aps-qfg@cnaps.com.cn
 * @Date: 2013-2-25上午9:53:24
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2013 版权所有
 * @version: V1.0
 */
public class SendFlag extends AbstractType{

	public static final Map ALL = new HashMap();
	
	public static final SendFlag SEND_Y = new SendFlag("可赠送", "0");
	public static final SendFlag SEND_N = new SendFlag("不可赠送", "1");
	
	protected SendFlag(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static SendFlag valueOf(String value) {
		SendFlag type = (SendFlag) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的标志");
		}
		return type;
	}
	
	public static List getAll(){
		return getOrderedList(SendFlag.ALL);
	}

}
