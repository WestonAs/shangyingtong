package gnete.card.entity.state;

import java.util.LinkedHashMap;
import java.util.Map;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

/**
 * @File: MakeCardRegState.java
 *
 * @description: 制卡登记状态
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-zwi
 * @version: 1.0
 * @since 1.0 2010-8-5
 */
public class MakeCardRegState extends AbstractState {
	public static final Map ALL = new LinkedHashMap();

	public static final MakeCardRegState WAITE_LOAD = new MakeCardRegState("待下载", "00");
	public static final MakeCardRegState WAITE_AUDIT = new MakeCardRegState("待审核", "10");	
	public static final MakeCardRegState EFFECTIVE = new MakeCardRegState("有效", "20");
	public static final MakeCardRegState INVALID = new MakeCardRegState("作废", "30");
	
	@SuppressWarnings("unchecked")
	protected MakeCardRegState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static MakeCardRegState valueOf(String value) {
		MakeCardRegState type = (MakeCardRegState) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("流程状态错误！");
		}

		return type;
	}
}
