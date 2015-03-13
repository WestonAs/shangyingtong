package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * @File: InsServiceType.java
 *
 * @description: 业务权限点
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @modify:
 * @version: 1.0
 * @since 1.0 2012-2-8
 */
public class InsServiceType extends AbstractType{

	public static final Map ALL = new HashMap();
	
	public static final InsServiceType DEPOSIT = new InsServiceType("储值卡", "00");
//	public static final InsServiceType SIGN = new InsServiceType("签单卡", "01");
	public static final InsServiceType DISCNT = new InsServiceType("折扣卡", "02");
	public static final InsServiceType MEMB = new InsServiceType("会员卡", "03");
	public static final InsServiceType COUPON_CARD = new InsServiceType("赠券卡", "04");
	public static final InsServiceType ACCU = new InsServiceType("次卡", "05");
	public static final InsServiceType POINT = new InsServiceType("积分", "06");
	public static final InsServiceType COUPON = new InsServiceType("赠券", "07");
	public static final InsServiceType DRAW = new InsServiceType("抽奖活动", "08");
	public static final InsServiceType VIRTUAL = new InsServiceType("虚拟账户", "10");
	
	protected InsServiceType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static InsServiceType valueOf(String value) {
		InsServiceType type = (InsServiceType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的类型");
		}
		return type;
	}
	
	/**
	 * 根据名称来排序
	 * @return
	 */
	public static List getAll(){
		return getOrderedList(InsServiceType.ALL);
	}

	/**
	 * 根据值来排序
	 * @return
	 */
	public static List getAllByValue(){
		return getValueOrderedList(InsServiceType.ALL);
	}
	
	/*
	 * 商户的业务类型只有"积分"、"赠券"、"抽奖活动"
	 */
	public static List getMerchServiceList() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(POINT.getValue(), POINT);
		map.put(COUPON.getValue(), COUPON);
		map.put(DRAW.getValue(), DRAW);
		
		return getOrderedList(map);
	}
}
