package gnete.card.entity.state;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

/**
 * @File: MemberCertifyState.java
 *
 * @description: 会员定义申请状态
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-lib
 * @version: 1.0
 * @since 1.0 2010-8-11
 */

public class MemberCertifyState extends AbstractState{
	
	public static final Map ALL = new HashMap();
	
	public static final MemberCertifyState WAITED = new MemberCertifyState("待审核", "00");
	public static final MemberCertifyState PASSED = new MemberCertifyState("审核通过", "10");	
	public static final MemberCertifyState FALURE = new MemberCertifyState("审核不通过", "20");

	@SuppressWarnings("unchecked")
	protected MemberCertifyState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static MemberCertifyState valueOf(String value) {
		MemberCertifyState type = (MemberCertifyState) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("审核状态错误！");
		}

		return type;
	}
	
	public static List getAll() {
		return getOrderedList(MemberCertifyState.ALL);
	}

}
