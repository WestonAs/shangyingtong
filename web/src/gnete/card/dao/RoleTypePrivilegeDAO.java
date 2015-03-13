package gnete.card.dao;

import gnete.card.entity.RoleTypePrivilege;

import java.util.List;

public interface RoleTypePrivilegeDAO extends BaseDAO {

	/**
	 * 根据类型查找角色权限列表
	 * @param roleType
	 * @return
	 */
	List<RoleTypePrivilege> findByType(String roleType);

	/**
	 * 删除某类型角色
	 * @param roleType
	 */
	int deleteByRoleType(String roleType);
}