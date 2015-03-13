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
public class PointSendApplyRegState extends AbstractState {
	public static final Map<String,PointSendApplyRegState> ALL = new LinkedHashMap<String,PointSendApplyRegState>();

	public static final PointSendApplyRegState WAITE_DEAL = new PointSendApplyRegState("待汇总", "00");
	public static final PointSendApplyRegState SUMMARIZING_SUCCESS = new PointSendApplyRegState("汇总成功", "01");
	public static final PointSendApplyRegState SUMMARIZING_FAILED = new PointSendApplyRegState("汇总失败", "02");
	public static final PointSendApplyRegState DEALING = new PointSendApplyRegState("赠送处理中", "03");
	public static final PointSendApplyRegState DEAL_SUCCESS = new PointSendApplyRegState("处理成功", "04");
	public static final PointSendApplyRegState DEAL_FAILED = new PointSendApplyRegState("处理失败", "05");
	
	protected PointSendApplyRegState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static PointSendApplyRegState valueOf(String value) {
		PointSendApplyRegState type = (PointSendApplyRegState) ALL.get(value);
		if (type == null) {
			throw new RuntimeBizException("状态错误！"); 
		}
		return type;
	}
	
	public static List<PointSendApplyRegState> getAll() {
		return new ArrayList<PointSendApplyRegState>(PointSendApplyRegState.ALL.values());
	}
}
