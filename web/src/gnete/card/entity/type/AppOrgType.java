package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

/**
 * @File: AppOrgType.java
 *
 * @description: 领卡机构类型
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-zwi
 * @version: 1.0
 * @since 1.0 2010-10-19
 */
public class AppOrgType extends AbstractType{
	
	public static final Map ALL = new HashMap();
	
	/*
	 	0 部门
		1 机构
	*/
	public static final AppOrgType DEPART = new AppOrgType("部门", "0");
	public static final AppOrgType BRANCH = new AppOrgType("发卡机构", "1");
	public static final AppOrgType MERCH = new AppOrgType("商户", "2");
	public static final AppOrgType PROXY = new AppOrgType("售卡代理", "3");
	
	@SuppressWarnings("unchecked")
	protected AppOrgType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static AppOrgType valueOf(String value) {
		AppOrgType type = (AppOrgType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的类型");
		}
		return type;
	}
	
	public static List getDept(){
		Map<String, Object>params = new HashMap<String, Object>();
		params.put(DEPART.getValue(), DEPART);
		return getOrderedList(params);
	}
	
	public static List getBranch(){
		Map<String, Object>params = new HashMap<String, Object>();
		params.put(BRANCH.getValue(), BRANCH);
		return getOrderedList(params);
	}
	
	public static List getMerch() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(MERCH.getValue(), MERCH);
		return getOrderedList(params);
	}
	
	public static List getDeptAndBranch() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(DEPART.getValue(), DEPART);
		params.put(BRANCH.getValue(), BRANCH);
		params.put(PROXY.getValue(), PROXY);
		return getOrderedList(params);
	}
	
	public static List getAll(){
		return getOrderedList(AppOrgType.ALL);
	}
	
}
