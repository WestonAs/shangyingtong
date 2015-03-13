package gnete.card.dao;

import flink.util.Paginater;
import gnete.card.entity.RoleInfo;

import java.util.List;
import java.util.Map;

public interface RoleInfoDAO extends BaseDAO {

	/**
	 * 查找当前用户拥有的角色
	 * 
	 * @param userId
	 * @return
	 */
	List<RoleInfo> findByUserId(String userId);
	
	/**
	 * 查找角色信息
	 * 
	 * @param params	查询参数信息
	 * 包括:
	 * <ul>
	 * 	<li>branchCode:String 机构代码</li>
	 * 	<li>merchCode:String 商户代码</li>
	 * 	<li>departmentCode:String 部门代码</li>
	 * 	<li>roleId:String 角色ID</li>
	 * 	<li>roleName:String 角色名称</li>
	 * </ul>
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater findRole(Map<String, Object> params, int pageNumber,
			int pageSize);

	/**
	 * 根据角色类型来查找通用角色
	 * @param roleType
	 * @return
	 */
	List<RoleInfo> findCommonByRoleType(String roleType);

	/**
	 * 查找可以分配的角色
	 * @param branchCode
	 * @param merchantNo
	 * @param deptId
	 * @return
	 */
	List<RoleInfo> findAssignRole(String branchCode, String merchantNo, String deptId);
	
	/**
	 * 根据角色名称查找角色列表
	 * @param roleName
	 * @return
	 */
	List<RoleInfo> findByRoleName(String roleName);
}