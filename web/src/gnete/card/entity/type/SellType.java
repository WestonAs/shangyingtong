package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * @File: SellType.java
 *
 * @description: 售卡机构类型
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: LiHeng
 * @modify: ZhaoWei
 * @version: 1.0
 * @since 1.0 2010-12-28 下午03:25:45
 */
public class SellType extends AbstractType {

	
	public static final Map ALL = new HashMap();
	
	/**
	 * 0：部门
     * 1：售卡机构
	 */
	public static final SellType DEPT = new SellType("部门","0");
	public static final SellType BRANCH = new SellType("售卡机构","1");
	
	@SuppressWarnings("unchecked")
	protected SellType(String name, String value) {
		
		super(name, value);
		ALL.put(value, this);
	}

	public static SellType valueOf(String value) {
		SellType cert = (SellType) ALL.get(value);
		
		if (cert == null) {
			throw new RuntimeBizException("不正确的类型");
		}
		return cert;
	}
	
	
	
	public static List getForDept(){
		Map params = new HashMap();
		params.put(DEPT.getValue(), DEPT);
		return getOrderedList(params);
	}
	
	public static List getForBranch(){
		Map params = new HashMap();
		params.put(BRANCH.getValue(), BRANCH);
		return getOrderedList(params);
	}
	
	public static List getAll(){
		return getOrderedList(SellType.ALL);
	}
}
