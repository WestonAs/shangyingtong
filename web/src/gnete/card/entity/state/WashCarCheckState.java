package gnete.card.entity.state;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * 缴费审核 登记状态
 * @author slt02
 *
 */
public class WashCarCheckState extends AbstractState{
	public static final Map ALL = new HashMap();

	public static final WashCarCheckState WAITED = new WashCarCheckState("待审核", "00");
	public static final WashCarCheckState PASSED = new WashCarCheckState("审核通过 ", "10");	
	public static final WashCarCheckState FALURE = new WashCarCheckState("审核不通过 ", "20");
	
	@SuppressWarnings("unchecked")
	protected WashCarCheckState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static WashCarCheckState valueOf(String value) {
		WashCarCheckState type = (WashCarCheckState) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("流程状态错误！");
		}
		
		return type;
	}
	
	public static List getAll(){
		return getOrderedList(WashCarCheckState.ALL);
	}
}
