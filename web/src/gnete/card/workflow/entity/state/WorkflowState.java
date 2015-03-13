package gnete.card.workflow.entity.state;

import java.util.HashMap;
import java.util.Map;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

/**
 * 流程状态
 * 
 * @author aps-lih
 */
public class WorkflowState extends AbstractState {
	public static final Map ALL = new HashMap();

	public static final WorkflowState RUNNING = new WorkflowState("运行中", "R");
	public static final WorkflowState FINISH = new WorkflowState("已结束", "F");	
	public static final WorkflowState BACKWORD = new WorkflowState("审核不通过", "B");	
	
	@SuppressWarnings("unchecked")
	protected WorkflowState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static WorkflowState valueOf(String value) {
		WorkflowState type = (WorkflowState) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("流程状态错误！");
		}

		return type;
	}
}
