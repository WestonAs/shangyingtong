package gnete.card.entity.state;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

/**
 * 积分累积赠送申请登记簿状态
 */
public class PointSendDetailState extends AbstractState {
	public static final Map<String,PointSendDetailState> ALL = new LinkedHashMap<String,PointSendDetailState>();

	public static final PointSendDetailState WAITED = new PointSendDetailState("待审核", "00");
	public static final PointSendDetailState PASSED = new PointSendDetailState("审核通过", "10");
	public static final PointSendDetailState UNPASSED = new PointSendDetailState("审核不通过", "20");
	public static final PointSendDetailState SEND_SUCCESS = new PointSendDetailState("赠送成功", "01");
	public static final PointSendDetailState SEND_FAILED = new PointSendDetailState("赠送失败", "02");
	
	protected PointSendDetailState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static PointSendDetailState valueOf(String value) {
		PointSendDetailState type = (PointSendDetailState) ALL.get(value);
		if (type == null) {
			throw new RuntimeBizException("状态错误！"); 
		}
		return type;
	}
	
	public static List<PointSendDetailState> getAll() {
		return new ArrayList<PointSendDetailState>(PointSendDetailState.ALL.values());
	}
}
