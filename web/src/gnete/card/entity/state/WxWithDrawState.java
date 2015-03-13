package gnete.card.entity.state;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;
/**
 * <li>微信提现查询状态</li>
 * @Project: cardWx
 * @File: WxPayDepositState.java
 *
 * @copyright: (c) 2014 ITECH INC.
 * @author: zhanghl
 * @modify:
 * @version: 1.0
 * @since 1.0 2014-4-18 下午03:26:34
 */
public class WxWithDrawState extends AbstractState {

	public static final Map<String, WxWithDrawState> ALL = new LinkedHashMap<String, WxWithDrawState>();
	
	/**
	 * 
	 * @param name
	 * @param value
	 * 
	 * 10 提交待后台处理
	11提现请求失败
	20 处理中
	30 等待返回
	90 支付失败
	00 已支付(处理成功)
	18待提出支付（等后台提交待收付文件）';
	 */
	
	public static final WxWithDrawState PRE_PROCESS = new WxWithDrawState("待处理", "10");
	
	public static final WxWithDrawState REQ_FAIL = new WxWithDrawState("请求失败", "11");
	
	public static final WxWithDrawState PROCESSING = new WxWithDrawState("处理中", "20");
	
	public static final WxWithDrawState WAIT_RETURN = new WxWithDrawState("等待返回", "30");
	
	public static final WxWithDrawState PAY_FAIL = new WxWithDrawState("支付失败", "90");
	
	public static final WxWithDrawState SUCCESS = new WxWithDrawState("已支付", "00");
	
	public static final WxWithDrawState WAIT_PAY = new WxWithDrawState("待提出支付", "18");
	
	protected WxWithDrawState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static WxWithDrawState valueOf(String value) {
		WxWithDrawState type = (WxWithDrawState) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("提现状态错误！");
		}
		return type;
	}
	
	@SuppressWarnings("unchecked")
	public static List<WxWithDrawState> getAll(){
		return getOrderedList(ALL);
	}
}
