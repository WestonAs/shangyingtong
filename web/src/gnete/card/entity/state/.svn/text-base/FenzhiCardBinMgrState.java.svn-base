package gnete.card.entity.state;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;


/**
 * @File: CustomerRebateState.java
 *
 * @description: 分支机构卡BIN分配表状态状态
 * <li>00: 可分配</li>
 * <li>10: 分配中</li>
 * <li>20: 已使用</li>
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2011-9-28
 */
public class FenzhiCardBinMgrState extends AbstractState {
	public static final Map ALL = new HashMap();

	public static final FenzhiCardBinMgrState UN_ASSIGN = new FenzhiCardBinMgrState("可分配", "00");
	public static final FenzhiCardBinMgrState ASSIGNING = new FenzhiCardBinMgrState("分配中", "10");	
	public static final FenzhiCardBinMgrState BEEN_USED = new FenzhiCardBinMgrState("已使用", "20");	
	
	@SuppressWarnings("unchecked")
	protected FenzhiCardBinMgrState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static FenzhiCardBinMgrState valueOf(String value) {
		FenzhiCardBinMgrState type = (FenzhiCardBinMgrState) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("状态错误！");
		}

		return type;
	}
	
	public static List getAll(){
		return getOrderedList(FenzhiCardBinMgrState.ALL);
	}

}
