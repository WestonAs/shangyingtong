package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * @File: MakeCardType.java
 *
 * @description: 制卡方式代码
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-zwi
 * @version: 1.0
 * @since 1.0 2010-8-5
 */
public class MakeCardType extends AbstractType{
	
	public static final Map ALL = new HashMap();
	
	/*
	  制卡方式代码
		00 厂商制卡写磁；
		01 厂商制卡不写磁
		02 自购打卡机打卡
	*/
	public static final MakeCardType MAKE_WRITE = new MakeCardType("厂商制卡写磁", "00");
	public static final MakeCardType PRINT_WRITE = new MakeCardType("厂商制卡不写磁", "01");
	public static final MakeCardType PUNCH_SELF = new MakeCardType("发卡机构自行打卡", "02");
	
	@SuppressWarnings("unchecked")
	protected MakeCardType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static MakeCardType valueOf(String value) {
		MakeCardType type = (MakeCardType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的卡类型");
		}
		return type;
	}
	
	public static List getAll(){
		return getOrderedList(MakeCardType.ALL);
	}
	
}
