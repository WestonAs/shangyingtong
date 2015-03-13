package gnete.card.entity.state;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

/**
 * @File: CheckState.java
 *
 * @description: 抽检结果
 * <li>00: 成功</li>
 * <li>10: 失败</li>
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-zwi
 * @version: 1.0
 * @since 1.0 2010-8-19
 */
public class CheckResult extends AbstractState {
	public static final Map ALL = new HashMap();

	public static final CheckResult SUCCESS = new CheckResult("成功", "00");
	public static final CheckResult FAILURE = new CheckResult("失败", "10");	
	
	@SuppressWarnings("unchecked")
	protected CheckResult(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static CheckResult valueOf(String value) {
		CheckResult type = (CheckResult) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("错误的抽检结果！");
		}

		return type;
	}
	
	public static List getAll(){
		return getOrderedList(CheckResult.ALL);
	}
}
