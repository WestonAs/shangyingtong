package gnete.card.entity.state;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

/**
 * @File: CheckState.java
 *
 * @description: 审核通用状态
 * <li>00: 待审核</li>
 * <li>10: 审核通过</li>
 * <li>20: 审核失败</li>
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-zwi
 * @version: 1.0
 * @since 1.0 2010-8-18
 */
public class CheckState extends AbstractState {
	public static final Map ALL = new HashMap();

	public static final CheckState WAITED = new CheckState("待审核", "00");
	public static final CheckState PASSED = new CheckState("审核通过", "10");	
	public static final CheckState FAILED = new CheckState("审核不通过", "20");
	
	@SuppressWarnings("unchecked")
	protected CheckState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static CheckState valueOf(String value) {
		CheckState type = (CheckState) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("流程状态错误！");
		}

		return type;
	}
	
	public static List getAll(){
		return getOrderedList(CheckState.ALL);
	}
}
