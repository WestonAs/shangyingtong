package gnete.card.entity.state;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

/**
 * <li>微信银行卡充值查询状态</li>
 * @Project: cardWx
 * @File: WxBankDepositState.java
 *
 * @copyright: (c) 2014 ITECH INC.
 * @author: zhanghl
 * @modify:
 * @version: 1.0
 * @since 1.0 2014-4-18 下午03:26:34
 */
public class WxBankDepositState extends AbstractState {

	public static final Map<String, WxBankDepositState> ALL = new LinkedHashMap<String, WxBankDepositState>();
	
	/**
	 * 
	 * @param name
	 * @param value
	 * 
	 * 10 待处理
	 * 20 处理中
	 * 30 待返回。（等待增值平台返回）
	 * 90 处理失败
	 * 00 处理成功
	 * 
	 */
	
	public static final WxBankDepositState PRE_PROCESS = new WxBankDepositState("待处理", "10");
	
	public static final WxBankDepositState PROCESSING = new WxBankDepositState("处理中", "20");
	
	public static final WxBankDepositState PRE_RETURN = new WxBankDepositState("待返回", "30");
	
	public static final WxBankDepositState FAIL = new WxBankDepositState("处理失败", "90");
	
	public static final WxBankDepositState SUCCESS = new WxBankDepositState("处理成功", "00");
	
	protected WxBankDepositState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static WxBankDepositState valueOf(String value) {
		WxBankDepositState type = (WxBankDepositState) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("便民充值状态错误！");
		}
		return type;
	}

	@SuppressWarnings("unchecked")
	public static List<WxBankDepositState> getAll(){
		return getOrderedList(ALL);
	}
}
