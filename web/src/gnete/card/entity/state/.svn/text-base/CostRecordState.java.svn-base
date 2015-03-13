package gnete.card.entity.state;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;


/**
 * @File: CostRecordState.java
 *
 * @description: 单机产品费用记录状态
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @modify:
 * @version: 1.0
 * @since 1.0 2012-2-9
 */
public class CostRecordState extends AbstractState {
	public static final Map ALL = new HashMap();

	public static final CostRecordState PAYED = new CostRecordState("已付款", "00");
	public static final CostRecordState UNPAY = new CostRecordState("未付款", "01");	
	public static final CostRecordState CANCEL = new CostRecordState("已撤销", "02");	
	
	@SuppressWarnings("unchecked")
	protected CostRecordState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static CostRecordState valueOf(String value) {
		CostRecordState type = (CostRecordState) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("状态错误！");
		}

		return type;
	}
	
	public static List getAll(){
		return getOrderedList(CostRecordState.ALL);
	}
}
