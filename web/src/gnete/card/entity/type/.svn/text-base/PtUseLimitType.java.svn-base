package gnete.card.entity.type;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @File: PtUseLimitType.java
 *
 * @description: 积分用途代码
 * 
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-lib
 * @version: 1.0
 * @since 1.0 2011-9-28
 */
public class PtUseLimitType extends AbstractType{
	public static final Map ALL = new HashMap();
	
	/**
	 * 00 积分消费
	 * 01 积分返利
	 * 02 积分兑换礼品
	 * 03 积分兑换赠券
	 */
	public static final PtUseLimitType POINT_CONSUME = new PtUseLimitType("积分消费", "00");
	public static final PtUseLimitType POINT_EXC = new PtUseLimitType("积分返利", "01");
	public static final PtUseLimitType POINT_EXC_GIFT = new PtUseLimitType("积分兑换礼品", "02");
	public static final PtUseLimitType POINT_EXC_COUPON = new PtUseLimitType("积分兑换赠券", "03");
	
	@SuppressWarnings("unchecked")
	protected PtUseLimitType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static PtUseLimitType valueOf(String value) {
		PtUseLimitType type = (PtUseLimitType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的类型");
		}
		return type;
	}
	
	public static List getAll(){
		return getValueOrderedList(PtUseLimitType.ALL);
	}

	public static List getAllCode(){
		List<String> codeList = new ArrayList<String>();
		codeList.add(POINT_CONSUME.getValue());
		codeList.add(POINT_EXC.getValue());
		codeList.add(POINT_EXC_GIFT.getValue());
		codeList.add(POINT_EXC_COUPON.getValue());
		return codeList;
	}
	
}
