package gnete.card.entity.state;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

/**
 * @File:EarnestMoneyState.java
 *
 * @description: 发卡机构准备金状态
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-lib
 * @version: 1.0
 * @since 1.0 2010-9-7
 */
public class EarnestMoneyState extends AbstractState{

	public static final Map ALL = new HashMap();

	public static final EarnestMoneyState NORMAL = new EarnestMoneyState("正常", "0");
	public static final EarnestMoneyState WARNING = new EarnestMoneyState("预警中 ", "2");	
	public static final EarnestMoneyState STOP = new EarnestMoneyState("停用 ", "3");
	
	protected EarnestMoneyState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static EarnestMoneyState valueOf(String value) {
		EarnestMoneyState type = (EarnestMoneyState) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("流程状态错误！");
		}
		
		return type;
	}
	
	public static List getAll(){
		return getOrderedList(EarnestMoneyState.ALL);
	}
}
