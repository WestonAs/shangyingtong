package gnete.card.entity.state;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

/**
 * @File: RegisterState.java
 *
 * @description: 登记簿状态
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-bey
 * @version: 1.0
 * @since 1.0 2010-7-27
 */
public class RegisterState extends AbstractState {
	public static final Map ALL = new LinkedHashMap();

	public static final RegisterState WAITED = new RegisterState("待审核", "00");
	public static final RegisterState PASSED = new RegisterState("审核通过 ", "10");	
	public static final RegisterState FALURE = new RegisterState("审核不通过 ", "20");
	public static final RegisterState NORMAL = new RegisterState("成功", "01");	
	public static final RegisterState DISABLE = new RegisterState("失败", "02");	
	public static final RegisterState WAITEDEAL = new RegisterState("待处理", "03");	
	public static final RegisterState INACTIVE = new RegisterState("未激活", "04");	
	public static final RegisterState CANCELED = new RegisterState("已撤销", "05");	
	public static final RegisterState REVOKED = new RegisterState("已取消", "06");	
	
	/*主要是批量预售卡，ws预售卡激活时用到 */
	public static final RegisterState DEALING = new RegisterState("处理中", "30");
	
	@SuppressWarnings("unchecked")
	protected RegisterState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static RegisterState valueOf(String value) {
		RegisterState type = (RegisterState) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("状态错误！");
		}

		return type;
	}
	
	/**
	 * 需要审核并发报文处理的登记薄用到的状态
	 * @return
	 */
	public static List<RegisterState> getForCheck(){
		ArrayList<RegisterState> list = new ArrayList<RegisterState>();
		list.add(WAITED);
		list.add(PASSED);
		list.add(FALURE);
		list.add(NORMAL);
		list.add(DISABLE);
		return list;
	}
	
	public static List getCheckStatus() {
		Map params = new HashMap();
		params.put(WAITED.getValue(), WAITED);
		params.put(PASSED.getValue(), PASSED);
		params.put(FALURE.getValue(), FALURE);

		return getOrderedList(params);
		
	}
	
	/**
	 * 不需要审核的交易登记薄用到的状态
	 * @return
	 */
	public static List getForTrade(){
		Map params = new HashMap();
		params.put(WAITEDEAL.getValue(), WAITEDEAL);
		params.put(NORMAL.getValue(), NORMAL);
		params.put(DISABLE.getValue(), DISABLE);
		return getOrderedList(params);
	}
	
	/**
	 *  售卡撤销列表页面用到的状态
	 * @return
	 */
	public static List getForSaleCancelList() {
		Map params = new HashMap();

		params.put(WAITED.getValue(), WAITED);
		params.put(NORMAL.getValue(), NORMAL);
		params.put(DISABLE.getValue(), DISABLE);
		params.put(WAITEDEAL.getValue(), WAITEDEAL);
		params.put(INACTIVE.getValue(), INACTIVE);
		params.put(CANCELED.getValue(), CANCELED);
		
		return getOrderedList(params);
	}
	
	/**
	 *  可撤销的售卡状态
	 * @return
	 */
	public static List getForSaleCancelDeal() {
		Map params = new HashMap();
		params.put(NORMAL.getValue(), NORMAL);
		params.put(INACTIVE.getValue(), INACTIVE);
		
		return getOrderedList(params);
	}
	
	public static List getAll() {
		return new ArrayList(ALL.values());
	}
}
