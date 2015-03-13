package gnete.card.entity.state;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

/***
 * 缴交会费状态
 * @author Luoxq
 *
 */
public class WashCarSvcMemberShipState extends AbstractState {
	public static final Map ALL = new HashMap();

	public static final WashCarSvcMemberShipState HAVE_TO_PAY = new WashCarSvcMemberShipState("已缴费", "1");
	public static final WashCarSvcMemberShipState NO_PAYMENT = new WashCarSvcMemberShipState("未缴费", "0");	
	
	@SuppressWarnings("unchecked")
	protected WashCarSvcMemberShipState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static WashCarSvcMemberShipState valueOf(String value) {
		WashCarSvcMemberShipState type = (WashCarSvcMemberShipState) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("缴交会员状态错误！");
		}

		return type;
	}
	
	public static List getAll(){
		return getOrderedList(WashCarSvcMemberShipState.ALL);
	}
}
