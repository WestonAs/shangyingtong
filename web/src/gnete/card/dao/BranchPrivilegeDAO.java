package gnete.card.dao;

import java.util.List;


public interface BranchPrivilegeDAO extends BaseDAO {

	/**
	 * 根据部门ID来删除权限数据
	 * @param deptId
	 * @return
	 */
	int deleteByDeptId(String deptId);

	/**
	 * 根据部门ID来查询
	 * @param deptId
	 * @return
	 */
	List findByDept(String deptId);

	
}