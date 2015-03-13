package gnete.card.entity.flag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractState;
import gnete.etc.RuntimeBizException;

/**
 * @File: HisDataFlag.java
 * 
 * @description: 历史数据迁移状态
 * 
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-lib
 * @version: 1.0
 * @since 1.0 2010-10-19
 */
public class HisDataFlag extends AbstractState{

	public static final Map ALL = new HashMap();

	public static final HisDataFlag INITIAL = new HisDataFlag("初始", "0");
	public static final HisDataFlag DEALING = new HisDataFlag("处理中", "1");
	public static final HisDataFlag SUCCESS = new HisDataFlag("处理完成", "2");
	public static final HisDataFlag FAILURE = new HisDataFlag("处理失败", "3");
	
	protected HisDataFlag(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static HisDataFlag valueOf(String value) {
		HisDataFlag type = (HisDataFlag) ALL.get(value);

		if (type == null) {
			throw new RuntimeBizException("流程状态错误！");
		}

		return type;
	}

	public static List getAll() {
		return getOrderedList(HisDataFlag.ALL);
	}
}
