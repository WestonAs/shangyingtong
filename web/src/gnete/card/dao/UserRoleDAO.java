package gnete.card.dao;

import gnete.card.entity.UserRoleKey;

import java.util.List;

public interface UserRoleDAO extends BaseDAO {

	/**
	 * 根据角色ID来查找用户信息
	 * @param roleId
	 * @return
	 */
	List<UserRoleKey> findByRoleId(String roleId);
	
	/**
	 * 根据用户ID来查找用户拥有的角色信息
	 * @param userId
	 * @return
	 */
	List<UserRoleKey> findByUserId(String userId);

	/**
	 * 删除用户拥有的角色信息
	 * @param userId
	 * @return
	 */
	int deleteByUserId(String userId);
}