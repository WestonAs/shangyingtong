package gnete.card.entity.state;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;
/**
 * <li>微信订单查询状态</li>
 * @Project: cardWx
 * @File: WxBillState.java
 *
 * @copyright: (c) 2014 ITECH INC.
 * @author: zhanghl
 * @modify:
 * @version: 1.0
 * @since 1.0 2014-4-18 下午03:26:34
 */
public class WxBillState extends AbstractState {

	public static final Map<String, WxBillState> ALL = new LinkedHashMap<String, WxBillState>();
//	public static final Map PRE = new HashMap();
	
	/*
	 * 订单状态状态
	10 待支付
	20 处理中
	03：已废除或取消，
	04：已过期，
	06：已退款
	30 待返回。（等待增值平台返回）
	90 支付失败
	00 处理成功(已付)
	15:.已受理(生成订单号,没有具体信息)
	16:订单已提交
	17:订单退款';
	*/
	
	public static final WxBillState PRE_PAY = new WxBillState("待支付", "10");
	
	public static final WxBillState PROCESSING = new WxBillState("处理中", "20");
	
	public static final WxBillState CANCEL = new WxBillState("已取消", "03");
	
	public static final WxBillState OUT_OF_DATE = new WxBillState("已过期", "04");
	
	public static final WxBillState REFUNDED = new WxBillState("已退款", "06");
	
	public static final WxBillState PRE_RETURN = new WxBillState("待返回", "30");
	
	public static final WxBillState FAIL = new WxBillState("支付失败", "90");
	
	public static final WxBillState SUCCESS = new WxBillState("处理成功", "00");
	
	public static final WxBillState PROCESSED = new WxBillState("已受理", "15");
	
	public static final WxBillState SUBMITED = new WxBillState("提交", "16");
	
	public static final WxBillState REFUNDING = new WxBillState("退款", "17");
	
	protected WxBillState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static WxBillState valueOf(String value) {
		WxBillState type = (WxBillState) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("订单状态错误！");
		}
		return type;
	}
	
	@SuppressWarnings("unchecked")
	public static List<WxBillState> getAll(){
		return getOrderedList(ALL);
	}
}
