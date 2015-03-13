package gnete.card.entity.state;

import java.util.HashMap;
import java.util.Map;
import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

public class RevcType extends AbstractState{

	public static final Map ALL = new HashMap();
	
	public static final RevcType ADJACC = new RevcType("调账", "71");
	public static final RevcType CANCEL_DEPOSIT = new RevcType("充值撤销", "72");
	public static final RevcType CANCEL = new RevcType("撤销", "90");	
	public static final RevcType RETURN_GOODS = new RevcType("退货 ", "91");
	public static final RevcType REFUND = new RevcType("冲正", "92");
	public static final RevcType CANCEL_REFUND = new RevcType("撤销冲正", "93");
	public static final RevcType CANCEL_GOODS = new RevcType("退货冲正", "94");
	
	protected RevcType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static RevcType valueOf(String value) {
		RevcType type = (RevcType) ALL.get(value);
		if (type == null) {
			throw new RuntimeBizException("流程状态错误！");
		}
		return type;
	}

}
