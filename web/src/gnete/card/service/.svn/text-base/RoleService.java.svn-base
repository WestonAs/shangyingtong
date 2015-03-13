package gnete.card.service;

import java.util.List;

import gnete.card.entity.CardPrivilegeGroup;
import gnete.card.entity.Privilege;
import gnete.card.entity.RoleInfo;
import gnete.card.entity.UserInfo;
import gnete.etc.BizException;

public interface RoleService {
	
	/**
	 * 创建角色
	 * @param roleInfo	角色信息
	 * @param privileges	权限信息
	 * @param createUserId	创建人信息
	 * @param isCommon 是否通用角色
	 */
	public void addRole(RoleInfo roleInfo, String[] privileges,
			UserInfo user, boolean isCommon) throws BizException ;
	
	/**
	 * 修改角色
	 * @param roleInfo
	 * @param privileges	权限信息
	 * @param updateUserId	更新人信息
	 */
	public void modifyRole(RoleInfo roleInfo, String[] privileges, 
			UserInfo user, boolean isCommon) throws BizException ;
	
	/**
	 * 删除角色
	 * @param roleId	角色编号
	 */
	public void deleteRole(String roleId) throws BizException ;

	/**
	 * 添加默认的管理员角色
	 * 
	 * @param refCode
	 * @param isBranch
	 * @param roleType
	 * @return
	 */
	public String addDefaultAdmin(String refCode, String name, boolean isBranch,
			String roleType, String userId) throws BizException ;

	/**
	 * 绑定用户和角色
	 * @param userId
	 * @param roleId
	 */
	public void bindUserRole(String userId, String roleId) throws BizException ;

	/**
	 * 查找我可以管理的权限
	 * @param sessionUser
	 * @return
	 */
	public List<Privilege> findManagePrivilge(UserInfo user);

	/**
	 * 查找这类型角色的所有权限
	 * @param value
	 * @return
	 */
	public List<Privilege> findPrivilgeByType(String value);

	/**
	 * 设置某类型角色权限
	 * @param roleType
	 * @param privileges
	 */
	public void setRoleTypePrivilege(String roleType, String[] privileges) throws BizException ;

	/**
	 * 添加发卡机构售卡代理权限组
	 * @param cardPrivilegeGroup
	 * @param array
	 */
	public void addCardPrivilegeGroup(CardPrivilegeGroup cardPrivilegeGroup,
			String[] array) throws BizException ;

	/**
	 * 修改发卡机构售卡代理权限组
	 * @param cardPrivilegeGroup
	 * @param array
	 */
	public void modifyCardPrivilegeGroup(CardPrivilegeGroup cardPrivilegeGroup,
			String[] array) throws BizException ;

	/**
	 * 删除发卡机构售卡代理权限组
	 * @param id
	 */
	public void deleteCardPrivilegeGroup(Long id) throws BizException ;

}
