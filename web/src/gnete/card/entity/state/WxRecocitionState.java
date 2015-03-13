package gnete.card.entity.state;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

/**
 * <li>微信实名卡扣款调账补帐后台处理状态</li>
 * @Project: cardWx
 * @File: WxRecocitionState.java
 *
 * @copyright: (c) 2014 ITECH INC.
 * @author: ZhaoWei
 * @modify:
 * @version: 1.0
 * @since 1.0 2014-4-17 下午03:26:34
 */
public class WxRecocitionState extends AbstractState {

	public static final Map<String, WxRecocitionState> ALL = new LinkedHashMap<String, WxRecocitionState>();
	
	/*
	 * 微信实名卡扣款调账补帐后台处理状态
	 * 10 待处理
     * 20 处理中
     * 90 处理失败
     * 00 处理成功
	*/
	
	public static final WxRecocitionState WAIT_DEAL = new WxRecocitionState("待处理", "10");
	public static final WxRecocitionState PROCESSING = new WxRecocitionState("处理中", "20");
	public static final WxRecocitionState FAILURE = new WxRecocitionState("处理失败", "90");
	public static final WxRecocitionState SUCCESS = new WxRecocitionState("处理成功", "00");
	
	protected WxRecocitionState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static WxRecocitionState valueOf(String value) {
		WxRecocitionState type = (WxRecocitionState) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("状态错误！");
		}
		return type;
	}
	
	@SuppressWarnings("unchecked")
	public static List<WxRecocitionState> getAll(){
		return getOrderedList(ALL);
	}
}
