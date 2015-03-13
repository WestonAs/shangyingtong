package gnete.card.dao;

import gnete.card.entity.PlanPrivilege;

import java.util.List;
import java.util.Map;

/**
 * @File: PlanPrivilegeDAO.java
 *
 * @description: 套餐业务权限表操作DAO
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @modify:
 * @version: 1.0
 * @since 1.0 2012-2-8
 */
public interface PlanPrivilegeDAO extends BaseDAO {
	
	/**
	 * 查找符合条件的套餐权限列表
	 * 
	 * @param params
	 * @return
	 */
	List<PlanPrivilege> findList(Map<String, Object> params);
}