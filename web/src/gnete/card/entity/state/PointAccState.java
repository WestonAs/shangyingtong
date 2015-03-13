package gnete.card.entity.state;

import java.util.HashMap;
import java.util.Map;
import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

/**
 * 积分充值及账户变更状态
 * 
 * @author aps-lib
 */
public class PointAccState extends AbstractState{

	public static final Map ALL = new HashMap();
	
	public static final PointAccState WAIT_EFFECT = new PointAccState("待生效", "00");
	public static final PointAccState CANCEL = new PointAccState("注销", "01");	
	public static final PointAccState EFFECT = new PointAccState("生效", "02");	
	public static final PointAccState FAILED = new PointAccState("失败", "03");	
	
	protected PointAccState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static PointAccState valueOf(String value) {
		PointAccState type = (PointAccState) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("状态错误！");
		}

		return type;
	}
}
