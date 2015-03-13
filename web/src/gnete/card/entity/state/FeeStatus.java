package gnete.card.entity.state;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

/**
 * 手续费状态
 * @author aps-lib
 */
public class FeeStatus extends AbstractState {

	public static final Map ALL = new HashMap();

	/*
		00：生效
		01：待生效
		02：已失效
	*/
	public static final FeeStatus EFFECT = new FeeStatus("生效", "00");
	public static final FeeStatus WAIT_EFFECT = new FeeStatus("待生效", "01");	
	public static final FeeStatus EXPIR = new FeeStatus("已失效", "02");	
	
	protected FeeStatus(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static FeeStatus valueOf(String value) {
		FeeStatus type = (FeeStatus) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("状态错误！");
		}

		return type;
	}
	
	public static List getList() {
		return getOrderedList(ALL);
	}

}
