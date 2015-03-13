package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * @File: BranchLevelType.java
 *
 * @description: 分支机构级别
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2011-8-23
 */
public class BranchLevelType extends AbstractType {

	
	public static final Map ALL = new HashMap();
	
	/**
	 * 分支机构级别
	 */
	public static final BranchLevelType FIRST = new BranchLevelType("一级","1");
	public static final BranchLevelType SECOND = new BranchLevelType("二级","2");
	public static final BranchLevelType THIRD = new BranchLevelType("三级","3");
	
	@SuppressWarnings("unchecked")
	protected BranchLevelType(String name, String value) {
		
		super(name, value);
		ALL.put(value, this);
	}

	public static BranchLevelType valueOf(String value) {
		BranchLevelType cert = (BranchLevelType) ALL.get(value);
		
		if (cert == null) {
			throw new RuntimeBizException("不正确的类型");
		}
		return cert;
	}
	
	public static List getAll(){
		return getValueOrderedList(BranchLevelType.ALL);
	}
	
	public static List getFirstList() {
		Map map = new HashMap();
		map.put(SECOND.getValue(), SECOND);
		map.put(THIRD.getValue(), THIRD);
		return getValueOrderedList(map);
	}
	
	public static List getSecondList(){
		Map map = new HashMap();
		map.put(THIRD.getValue(), THIRD);
		return getValueOrderedList(map);
	}
}
