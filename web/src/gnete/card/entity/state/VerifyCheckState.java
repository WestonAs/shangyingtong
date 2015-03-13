package gnete.card.entity.state;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

/**
 * @File:VerifyCheckState.java
 *
 * @description: 核销状态
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-lib
 * @version: 1.0
 * @since 1.0 2010-8-13
 */
public class VerifyCheckState extends AbstractState{
	
	public static final Map ALL = new HashMap();
	
	public static final VerifyCheckState INITIAL = new VerifyCheckState("初始", "00");
	public static final VerifyCheckState WAIT_VERIFY = new VerifyCheckState("待核销 ", "01");	
	public static final VerifyCheckState EQUAL_VERIFY = new VerifyCheckState("等额核销", "02");
	public static final VerifyCheckState DIFFER_VERIFY = new VerifyCheckState("差额核销", "03");
	public static final VerifyCheckState PART_VERIFY = new VerifyCheckState("部分核销", "04");
	public static final VerifyCheckState EXPORT_VERIFY = new VerifyCheckState("已导出", "05");
	public static final VerifyCheckState DIFFER_WAITE = new VerifyCheckState("差额待结转", "06");

	protected VerifyCheckState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static VerifyCheckState valueOf(String value) {
		VerifyCheckState type = (VerifyCheckState) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("流程状态错误！");
		}
		return type;
	}
	
	public static List getList() {
		return getOrderedList(ALL);
	}
}
