package gnete.card.entity.state;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

/**
 * <li>微信对账结果明细表状态</li>
 * @Project: cardWx
 * @File: WxRecocitionDetailState.java
 *
 * @copyright: (c) 2014 ITECH INC.
 * @author: ZhaoWei
 * @modify:
 * @version: 1.0
 * @since 1.0 2014-4-17 下午03:26:34
 */
public class WxRecocitionDetailState extends AbstractState {

	public static final Map<String, WxRecocitionDetailState> ALL = new LinkedHashMap<String, WxRecocitionDetailState>();
	
	/**
	  * 对账结果处理状态
	  * 0：未处理
	  * 1：不需要处理
	  * 2：补帐成功
	  * 3：调账成功
	*/
	
	public static final WxRecocitionDetailState UN_DEAL = new WxRecocitionDetailState("未处理", "0");
	public static final WxRecocitionDetailState UNNECESSARY = new WxRecocitionDetailState("不需要处理", "1");
	public static final WxRecocitionDetailState FILL_SUCESS = new WxRecocitionDetailState("补帐成功", "2");
	public static final WxRecocitionDetailState FIT_SUCCESS = new WxRecocitionDetailState("调账成功", "3");
	
	protected WxRecocitionDetailState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static WxRecocitionDetailState valueOf(String value) {
		WxRecocitionDetailState type = (WxRecocitionDetailState) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("状态错误！");
		}
		return type;
	}
	
	@SuppressWarnings("unchecked")
	public static List<WxRecocitionDetailState> getAll(){
		return getOrderedList(ALL);
	}
}
