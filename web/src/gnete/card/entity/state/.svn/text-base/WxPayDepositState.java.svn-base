package gnete.card.entity.state;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;
/**
 * <li>微信便民充值查询状态</li>
 * @Project: cardWx
 * @File: WxPayDepositState.java
 *
 * @copyright: (c) 2014 ITECH INC.
 * @author: zhanghl
 * @modify:
 * @version: 1.0
 * @since 1.0 2014-4-18 下午03:26:34
 */
public class WxPayDepositState extends AbstractState {

	public static final Map<String, WxPayDepositState> ALL = new LinkedHashMap<String, WxPayDepositState>();
	
	/**
	 * 10 待充值
	20 处理中
	03：已废除或取消，
	04：已过期，
	06：已退款
	30 待返回。（等待增值平台返回）
	90 充值失败
	00 处理成功(已充值)
	15:.已受理(生成订单号,没有具体信息)
	16:订单已提交';
	*/
	
	public static final WxPayDepositState PRE_DEPOSIT = new WxPayDepositState("待充值", "10");
	
	public static final WxPayDepositState PROCESSING = new WxPayDepositState("处理中", "20");
	
	public static final WxPayDepositState CANCEL = new WxPayDepositState("已取消", "03");
	
	public static final WxPayDepositState OVERRIDE = new WxPayDepositState("已过期", "04");
	
	public static final WxPayDepositState PAYBACK = new WxPayDepositState("已退款", "06");
	
	public static final WxPayDepositState PRE_RETURN = new WxPayDepositState("待返回", "30");
	
	public static final WxPayDepositState FAIL = new WxPayDepositState("充值失败", "90");
	
	public static final WxPayDepositState SUCCESS = new WxPayDepositState("处理成功", "00");
	
	public static final WxPayDepositState ACCEPTED = new WxPayDepositState("已受理", "15");
	
	public static final WxPayDepositState COMMITED = new WxPayDepositState("已提交", "16");
	
	protected WxPayDepositState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static WxPayDepositState valueOf(String value) {
		WxPayDepositState type = (WxPayDepositState) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("便民充值状态错误！");
		}
		return type;
	}

	@SuppressWarnings("unchecked")
	public static List<WxPayDepositState> getAll(){
		return getOrderedList(ALL);
	}
}
