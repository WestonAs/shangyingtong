package gnete.card.entity.flag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * @File: GroupFlag.java
 *
 * @description: 登记簿状态
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-lib
 * @version: 1.0
 * @since 1.0 2011-6-2
 */
public class GroupFlag extends AbstractType{

	public static final Map ALL = new HashMap();

	public static final GroupFlag ALLOW_OTHER = new GroupFlag("发卡机构", "0");
//	public static final GroupFlag NOT_ALLOW = new GroupFlag("集团", "1");
	
	protected GroupFlag(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static GroupFlag valueOf(String value) {
		GroupFlag type = (GroupFlag) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的标志");
		}
		return type;
	}
	
	public static List getAll(){
		return getOrderedList(GroupFlag.ALL);
	}

}
