package gnete.card.entity.flag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * @File: CardFlag.java
 *
 * @description: 卡片类型
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @modify:
 * @version: 1.0
 * @since 1.0 2011-12-7
 */
public class CardFlag extends AbstractType{

	public static final Map ALL = new HashMap();
	
	public static final CardFlag CARD = new CardFlag("磁条卡", "00");
	public static final CardFlag IC = new CardFlag("纯IC卡", "01");
	public static final CardFlag COMBINE = new CardFlag("复合卡", "02");
	public static final CardFlag M1 = new CardFlag("M1卡", "03");
	
	protected CardFlag(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static CardFlag valueOf(String value) {
		CardFlag type = (CardFlag) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的标志");
		}
		return type;
	}
	
	public static List getAll(){
		return getValueOrderedList(CardFlag.ALL);
	}

}
