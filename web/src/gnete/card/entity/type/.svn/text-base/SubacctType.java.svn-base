package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

/**
 * @File: SubacctType.java
 *
 * @description: 子账户类型
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: aps-bey
 * @version: 1.0
 * @since 1.0 2010-8-23
 */
public class SubacctType extends AbstractState {
	public static final Map ALL = new HashMap();

	public static final SubacctType DEPOSIT = new SubacctType("充值资金", "01");
	public static final SubacctType REBATE = new SubacctType("返利资金 ", "02");	
	/*已取消，合并到返利资金
	public static final SubacctType POINT = new SubacctType("积分兑换资金 ", "03");*/
	public static final SubacctType COUPON = new SubacctType("赠券资金", "04");
	public static final SubacctType ACCU = new SubacctType("次卡子帐户", "05");	
	
	public static final SubacctType SIGN_BALANCE = new SubacctType("签单余额", "06");	
	public static final SubacctType JINS_COUPON = new SubacctType("联名赠券帐户", "07");	

	public static final SubacctType E_CASH = new SubacctType("IC卡电子现金账户", "20");	
	public static final SubacctType FILL_UP = new SubacctType("IC卡补登帐户", "21");	
	public static final SubacctType IC_WAITE_SET = new SubacctType("IC卡待清算账户", "22");	
	
	@SuppressWarnings("unchecked")
	protected SubacctType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static SubacctType valueOf(String value) {
		SubacctType type = (SubacctType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("流程状态错误！");
		}

		return type;
	}

	public static List getAll(){
		return getOrderedList(SubacctType.ALL);
	}
	
	public static List getListWithoutIC() {
		Map params = new HashMap();
		params.put(DEPOSIT.getValue(), DEPOSIT);
		params.put(REBATE.getValue(), REBATE);
		params.put(COUPON.getValue(), COUPON);
		params.put(ACCU.getValue(), ACCU);
		params.put(JINS_COUPON.getValue(), JINS_COUPON);
		
		return getOrderedList(params);
	}
	
	// 赠券卡获得赠券账户列表
	public static List getCouponAcct(){
		Map params = new HashMap();
		params.put(COUPON.getValue(), COUPON);
		return getOrderedList(params);
	}
	
	// 非赠券卡获得充值、返利和次卡子账户列表
	public static List getNotCouponAcct(){
		Map params = new HashMap();
		params.put(DEPOSIT.getValue(), DEPOSIT);
		params.put(REBATE.getValue(), REBATE);
		params.put(ACCU.getValue(), ACCU);
		return getOrderedList(params);
	}
	
}
