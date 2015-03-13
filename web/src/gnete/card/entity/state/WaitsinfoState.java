package gnete.card.entity.state;

import java.util.HashMap;
import java.util.Map;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

/**
 * 内部命令状态
 * 
 * @author aps-lih
 */
public class WaitsinfoState extends AbstractState {
	public static final Map ALL = new HashMap();

	public static final WaitsinfoState UNDEAL = new WaitsinfoState("未处理", "0");
	public static final WaitsinfoState CALLFAILED = new WaitsinfoState("调用成功,正在处理", "1");
	public static final WaitsinfoState DEALING = new WaitsinfoState("调用失败", "2");
	public static final WaitsinfoState DEALFAILED = new WaitsinfoState("处理失败", "3");
	public static final WaitsinfoState SUCCESS = new WaitsinfoState("处理成功", "4");
	
	@SuppressWarnings("unchecked")
	protected WaitsinfoState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static WaitsinfoState valueOf(String value) {
		WaitsinfoState type = (WaitsinfoState) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("内部命令状态错误！");
		}

		return type;
	}
}
