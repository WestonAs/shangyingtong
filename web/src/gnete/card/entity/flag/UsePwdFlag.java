package gnete.card.entity.flag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * @File: UsePwdFlag.java
 *
 * @author: aps-lih
 * @since 1.0 2010-9-10
 */
public class UsePwdFlag extends AbstractType {
	public static final Map ALL = new HashMap();

	/**
	  0 不使用 ,1 使用密码
	 */
	public static final UsePwdFlag NO = new UsePwdFlag("不使用密码", "0");
	public static final UsePwdFlag YES = new UsePwdFlag("使用密码", "1");
	public static final UsePwdFlag BRANCH = new UsePwdFlag("根据卡的设置", "2");
	
	@SuppressWarnings("unchecked")
	protected UsePwdFlag(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static UsePwdFlag valueOf(String value) {
		UsePwdFlag type = (UsePwdFlag) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的代码");
		}
		return type;
	}
	
	public static List getAll(){
		return getOrderedList(UsePwdFlag.ALL);
	}
}
