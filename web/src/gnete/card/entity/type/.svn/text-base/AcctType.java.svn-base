package gnete.card.entity.type;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * @File: AcctType.java
 * @description: 账户类型代码
 */
public class AcctType extends AbstractType{
	public static final Map ALL = new LinkedHashMap();
	
	/** 0 私人 */
	public static final AcctType PRIVATE_ACCT = new AcctType("私人账户", "0");
 	/** 1 公司 */
	public static final AcctType COMPANY = new AcctType("公司账户", "1");
	
	@SuppressWarnings("unchecked")
	protected AcctType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static AcctType valueOf(String value) {
		AcctType type = (AcctType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的类型");
		}
		return type;
	}
	
	public static List getAll(){
		return new ArrayList(AcctType.ALL.values());
	}
	
}
