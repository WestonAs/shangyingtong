package gnete.card.dao;

import flink.util.Paginater;
import gnete.card.entity.DepartmentInfo;

import java.util.List;
import java.util.Map;

public interface DepartmentInfoDAO extends BaseDAO {

	/**
	 * 查找部门信息
	 * 
	 * @param params
	 *  * 包括:
	 * <ul>
	 * 	<li>deptId:String 部门代码</li>
	 * 	<li>deptName:String 部门名称</li>
	 * 	<li>branchCode:String 机构代码</li>
	 * </ul>
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater find(Map<String, Object> params, int pageNumber, int pageSize);
	
	List<DepartmentInfo> findByBranch(String branchCode);
	
	List<DepartmentInfo> findDeptList(Map<String, Object> params);

}