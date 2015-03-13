package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * @File: MerchLevel.java
 *
 * @author: aps-lih
 * @since 1.0 2010-9-10
 */
public class MerchLevel extends AbstractType {
	public static final Map ALL = new HashMap();

	/**
	  0 集团 、1: 1级分支机构 、2:2 级分支机构
	 */
	public static final MerchLevel JI_TUAN = new MerchLevel("集团", "0");
	public static final MerchLevel YI_JI = new MerchLevel("1级分支机构", "1");
	public static final MerchLevel ER_JI = new MerchLevel("2级分支机构", "2");
	
	@SuppressWarnings("unchecked")
	protected MerchLevel(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static MerchLevel valueOf(String value) {
		MerchLevel type = (MerchLevel) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的代码");
		}
		return type;
	}
	
	public static List getAll(){
		return getOrderedList(MerchLevel.ALL);
	}
}
