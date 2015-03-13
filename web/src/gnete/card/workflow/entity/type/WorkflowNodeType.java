package gnete.card.workflow.entity.type;

import java.util.HashMap;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * @author aps-lih
 */
public class WorkflowNodeType extends AbstractType {
	public static final Map ALL = new HashMap();

	public static final WorkflowNodeType ENTRY = new WorkflowNodeType("入口节点", "0");
	public static final WorkflowNodeType COMMON = new WorkflowNodeType("普通审批节点", "1");
	public static final WorkflowNodeType MULTITRANS = new WorkflowNodeType("协同处理节点", "2");
	
	@SuppressWarnings("unchecked")
	protected WorkflowNodeType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static WorkflowNodeType valueOf(String value) {
		WorkflowNodeType type = (WorkflowNodeType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("工作流节点类型错误");
		}

		return type;
	}
}
