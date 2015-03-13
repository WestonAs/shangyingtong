package gnete.card.entity.state;

import java.util.HashMap;
import java.util.Map;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

/**
 * 赠券子账户首先使用状态
 * 
 * @author aps-bey
 */
public class CouponUseState extends AbstractState {
	public static final Map ALL = new HashMap();

	public static final CouponUseState FIRSTUSE = new CouponUseState("先用赠券账户", "0");
	public static final CouponUseState LASTUSE = new CouponUseState("不先用赠券账户", "1");	
	
	@SuppressWarnings("unchecked")
	protected CouponUseState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static CouponUseState valueOf(String value) {
		CouponUseState type = (CouponUseState) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("状态错误！");
		}

		return type;
	}
}
