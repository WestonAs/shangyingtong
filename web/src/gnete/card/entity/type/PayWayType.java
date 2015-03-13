package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

public class PayWayType extends AbstractType{

	public static final Map ALL = new HashMap();
	/**
	 * 00系统内支付（普通交易）、系统外支付（消费赠送），
     * 01只支持系统内支付（普通交易），
     * 02只支持系统外支付（消费赠送）
	 */
	public static final PayWayType BOTH_PAY = new PayWayType("支持系统内/外支付","00");
	public static final PayWayType IN_PAY = new PayWayType("只支持系统内支付（普通交易）", "01");
	public static final PayWayType OUT_PAY = new PayWayType("只支持系统外支付（消费赠送）", "02");
	
	protected PayWayType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static PayWayType valueOf(String value) {
		PayWayType type = (PayWayType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的类型");
		}
		return type;
	}
	
	public static List getAll(){
		return getOrderedList(PayWayType.ALL);
	}
}
