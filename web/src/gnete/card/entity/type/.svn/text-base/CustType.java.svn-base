package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * 
 * @author libaozhu
 * @date 2013-2-26
 */
public class CustType extends AbstractType{
	public static final Map ALL = new HashMap();
	
	public static final CustType TYPE_MERCHANT = new CustType("商户", "0");
	public static final CustType TYPE_BRANCH = new CustType("机构", "1");
	
	@SuppressWarnings("unchecked")
	protected CustType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static CustType valueOf(String value) {
		CustType type = (CustType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("类型错误");
		}
		return type;
	}
	
	public static List getAll(){
		return getValueOrderedList(CustType.ALL);
	}
	
}
