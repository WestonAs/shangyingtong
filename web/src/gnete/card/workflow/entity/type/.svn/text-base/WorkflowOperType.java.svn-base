package gnete.card.workflow.entity.type;

import java.util.HashMap;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * @author aps-lih
 */
public class WorkflowOperType extends AbstractType {
	public static final Map ALL = new HashMap();

	public static final WorkflowOperType REGISTER = new WorkflowOperType("录入", "0");
	public static final WorkflowOperType CHECK_PASS = new WorkflowOperType("审核通过", "1");
	public static final WorkflowOperType CHECK_NOPASS = new WorkflowOperType("审核不通过", "2");
	
	@SuppressWarnings("unchecked")
	protected WorkflowOperType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static WorkflowOperType valueOf(String value) {
		WorkflowOperType type = (WorkflowOperType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("工作流操作类型错误");
		}

		return type;
	}
}
