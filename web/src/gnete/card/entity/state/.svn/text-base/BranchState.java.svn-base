package gnete.card.entity.state;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

/**
 * 机构状态
 * 
 * @author aps-lih
 */
public class BranchState extends AbstractState {
	public static final Map ALL = new HashMap();

	public static final BranchState NORMAL = new BranchState("启用", "00");
	public static final BranchState STOPED = new BranchState("停用", "10");
	public static final BranchState WAITED = new BranchState("待审核", "20");
	public static final BranchState UNPASS = new BranchState("审核不通过", "30");
	public static final BranchState PRESUB = new BranchState("待提交", "40");
	
	@SuppressWarnings("unchecked")
	protected BranchState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static BranchState valueOf(String value) {
		BranchState type = (BranchState) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("流程状态错误！");
		}

		return type;
	}
	
	public static List getAll(){
		return getOrderedList(BranchState.ALL);
	}
}
