package gnete.card.entity.state;

import java.util.HashMap;
import java.util.Map;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

/**
 * 代理关系状态
 * 
 * @author aps-lih
 */
public class DepartmentState extends AbstractState {
	public static final Map ALL = new HashMap();

	public static final DepartmentState NORMAL = new DepartmentState("正常", "00");
	public static final DepartmentState CANCEL = new DepartmentState("注销", "10");	
	
	@SuppressWarnings("unchecked")
	protected DepartmentState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static DepartmentState valueOf(String value) {
		DepartmentState type = (DepartmentState) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("部门状态错误！");
		}

		return type;
	}
}
