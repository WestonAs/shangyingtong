package gnete.card.entity.state;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

/**
 * <li>微信实名卡扣款对账明细表状态</li>
 * @File: WxRecocitionProcState.java
 *
 * @copyright: (c) 2014 ITECH INC.
 * @author: ZhaoWei
 * @modify:
 * @version: 1.0
 * @since 1.0 2014-4-17 下午03:26:34
 */
public class WxRecocitionProcState extends AbstractState {

	public static final Map<String, WxRecocitionProcState> ALL = new LinkedHashMap<String, WxRecocitionProcState>();
	
	/*
	 * 微信实名卡扣款调账补帐后台处理状态
	 * 10 待处理
     * 20 处理中
     * 90 处理失败
     * 00 处理成功
	*/
	
	public static final WxRecocitionProcState WAIT_DEAL = new WxRecocitionProcState("未处理", "0");
	public static final WxRecocitionProcState UNESSARY = new WxRecocitionProcState("不需要处理", "1");
	public static final WxRecocitionProcState FILL_SUCCESS = new WxRecocitionProcState("补帐成功", "2");
	public static final WxRecocitionProcState FIT_SUCCESS = new WxRecocitionProcState("调账成功", "3");
	
	protected WxRecocitionProcState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static WxRecocitionProcState valueOf(String value) {
		WxRecocitionProcState type = (WxRecocitionProcState) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("状态错误！");
		}
		return type;
	}
	
	@SuppressWarnings("unchecked")
	public static List<WxRecocitionProcState> getAll(){
		return getOrderedList(ALL);
	}
}
