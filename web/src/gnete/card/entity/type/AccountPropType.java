package gnete.card.entity.type;

import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * 
 * @author libaozhu
 * @date 2013-3-18
 */
public class AccountPropType extends AbstractType {
	public static final Map ALL = new java.util.LinkedHashMap();

	public static final AccountPropType PERSONAL = new AccountPropType("私人", "0");
	public static final AccountPropType COMPANY = new AccountPropType("公司", "1");

	public static AccountPropType valueOf(String value) {
		AccountPropType type = (AccountPropType) ALL.get(value);

		if (type == null) {
			throw new RuntimeBizException("类型错误");
		}

		return type;
	}

	@SuppressWarnings("unchecked")
	protected AccountPropType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	public static List getAll(){
		return getValueOrderedList(AccountPropType.ALL);
	}
}
