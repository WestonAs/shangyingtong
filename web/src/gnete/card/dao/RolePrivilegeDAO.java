package gnete.card.dao;

import java.util.Map;

public interface RolePrivilegeDAO extends BaseDAO {

	/**
	 * 根据角色ID删除
	 * @param roleId
	 * @return
	 */
	int deleteByRoleId(String roleId);
	
	/**
	 * 判断某个角色是否拥有某个权限点
	 * @param params
	 * @return
	 */
	boolean hasPrivilege(Map<String, Object> params);
}