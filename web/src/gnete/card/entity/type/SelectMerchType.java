package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

public class SelectMerchType extends AbstractType {

	
	public static final Map ALL = new HashMap();
	
	/**
	 * 选择商户类型
	 */
	public static final SelectMerchType SINGLE = new SelectMerchType("商户","0");
	public static final SelectMerchType GROUP = new SelectMerchType("商户组","1");
	
	@SuppressWarnings("unchecked")
	protected SelectMerchType(String name, String value) {
		
		super(name, value);
		ALL.put(value, this);
	}

	public static SelectMerchType valueOf(String value) {
		SelectMerchType cert = (SelectMerchType) ALL.get(value);
		
		if (cert == null) {
			throw new RuntimeBizException("不正确的类型");
		}
		return cert;
	}
	
	public static List getAll(){
		return getOrderedList(SelectMerchType.ALL);
	}
}
