package gnete.card.workflow.entity.type;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * @author aps-lih
 */
public class WorkflowNodeFollow extends AbstractType {
	public static final LinkedHashMap ALL = new LinkedHashMap();

	public static final WorkflowNodeFollow NORMAL = new WorkflowNodeFollow("审批人不限制", "0");
	
	public static final WorkflowNodeFollow REG = new WorkflowNodeFollow("审批人必须与申请人处于同一机构或商户", "1");
	
	public static final WorkflowNodeFollow SELF_SUPER = new WorkflowNodeFollow("审批机构必须是申请机构或其上级机构", "5");
	
	public static final WorkflowNodeFollow PROXY_CARD = new WorkflowNodeFollow("审批人必须是与该申请人所属机构有特约关系的发卡机构", "2");
	public static final WorkflowNodeFollow FENZHI = new WorkflowNodeFollow("审批人必须是该申请人所属机构或商户的管理分支机构", "3");
	
	@SuppressWarnings("unchecked")
	protected WorkflowNodeFollow(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static WorkflowNodeFollow valueOf(String value) {
		WorkflowNodeFollow type = (WorkflowNodeFollow) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("工作流节点类型错误");
		}

		return type;
	}
	
	public static List getAll(){
		return new ArrayList(WorkflowNodeFollow.ALL.values());
	}
}
