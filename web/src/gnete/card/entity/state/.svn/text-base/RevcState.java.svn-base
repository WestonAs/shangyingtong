package gnete.card.entity.state;

import java.util.HashMap;
import java.util.Map;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

public class RevcState extends AbstractState{

	public static final Map ALL = new HashMap();
	
	public static final RevcState RECEIVED = new RevcState("请求已接收", "00");
	public static final RevcState SUCCESS = new RevcState("处理成功 ", "01");	
	public static final RevcState FAILURE = new RevcState("处理失败 ", "02");
	public static final RevcState REFUNDED = new RevcState("已冲正", "04");
	public static final RevcState CANCELED = new RevcState("已撤销", "03");
	
	protected RevcState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static RevcState valueOf(String value) {
		RevcState type = (RevcState) ALL.get(value);
		if (type == null) {
			throw new RuntimeBizException("流程状态错误！");
		}
		return type;
	}

}
