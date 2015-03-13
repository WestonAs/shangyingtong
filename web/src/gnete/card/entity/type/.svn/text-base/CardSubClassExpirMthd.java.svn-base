package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * @File: CardSubClassExpirMthd.java
 *
 * @description: 失效方式
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2011-1-14
 */
public class CardSubClassExpirMthd extends AbstractType {
	
	public static final Map ALL = new HashMap();
	
	/**
	 * 证件类型
	 */
	public static final CardSubClassExpirMthd SPECIFY_DATE = new CardSubClassExpirMthd("指定失效日期","1");
	public static final CardSubClassExpirMthd SPECIF_MOTHS = new CardSubClassExpirMthd("指定失效月数","2");
	
	@SuppressWarnings("unchecked")
	protected CardSubClassExpirMthd(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static CardSubClassExpirMthd valueOf(String value) {
		CardSubClassExpirMthd type = (CardSubClassExpirMthd) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的类型");
		}
		return type;
	}
	
	public static List getAll(){
		return getOrderedList(CardSubClassExpirMthd.ALL);
	}
}
