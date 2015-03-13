package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * 密码类型代码:	0-固定密码；1-随机密码
 */
public class PasswordType extends AbstractType{
	public static final Map ALL = new HashMap();
	
	public static final PasswordType FIXED = new PasswordType("固定密码", "0");
	public static final PasswordType RANDOM = new PasswordType("随机密码", "1");
	
	@SuppressWarnings("unchecked")
	protected PasswordType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static PasswordType valueOf(String value) {
		PasswordType type = (PasswordType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的类型");
		}
		return type;
	}
	
	public static List getAll(){
		return getOrderedList(PasswordType.ALL);
	}
	
	
}
