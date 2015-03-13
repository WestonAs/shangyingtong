package gnete.card.dao;

import gnete.card.entity.Privilege;

import java.util.List;

public interface PrivilegeDAO extends BaseDAO {
	
	/**
	 * 获取该角色的菜单.
	 * 
	 * @param roleId 角色编号.
	 * @return map(limitId:String, limitName:String, parent:String)
	 */
	List getMenus(String roleId);
	
	/**
	 * 获取用户所有权限点, 不包括菜单.
	 * 
	 * @param userId 用户编号.
	 * @return Privilege
	 */
	List<Privilege> getPrivilege(String roleId);

	/**
	 * 根据角色ID查找其拥有权限
	 * @param roleId
	 * @return
	 */
	List findByRoleId(String roleId);

	/**
	 * 根据售卡代理机构和对应发卡机构的ID来查找权限
	 * @param proxyId
	 * @param cardBranch
	 * @return
	 */
	List<Privilege> findByProxyAndCard(String proxyId, String cardBranch);
	
	/**
	 * 根据部门来读取权限
	 * @param branchNo	部门所属机构
	 * @param deptId	部门编号
	 * @return
	 */
	List<Privilege> findByDept(String branchNo, String deptId);

	/**
	 * 根据角色类型来读取权限
	 * @param roleType
	 * @return
	 */
	List<Privilege> findByRoleType(String roleType);

}