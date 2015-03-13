package gnete.card.workflow.entity.state;

import java.util.HashMap;
import java.util.Map;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

/**
 * 流程待办状态
 * 
 * @author aps-lih
 */
public class WorkflowTodoState extends AbstractState {
	public static final Map ALL = new HashMap();

	public static final WorkflowTodoState TODO = new WorkflowTodoState("待办", "0");
	public static final WorkflowTodoState DONE = new WorkflowTodoState("已办", "1");	
	
	@SuppressWarnings("unchecked")
	protected WorkflowTodoState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static WorkflowTodoState valueOf(String value) {
		WorkflowTodoState type = (WorkflowTodoState) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("流程待办任务状态错误！");
		}

		return type;
	}
}
