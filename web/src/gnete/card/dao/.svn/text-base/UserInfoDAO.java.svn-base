package gnete.card.dao;

import flink.util.Paginater;
import gnete.card.entity.UserInfo;

import java.util.List;
import java.util.Map;


public interface UserInfoDAO extends BaseDAO {
	
	/**
	 * 查找用户
	 * 
	 * @param params
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	Paginater findUser(Map params, int pageNo, int pageSize);

	/**
	 * 根据机构或部门查询用户
	 * @param branchCode
	 * @param deptId
	 * @return
	 */
	List<UserInfo> findByBranch(String branchCode, String deptId);
	
	/**
	 * 
	  * @description：
	  * @param params
	  * @return  
	  * @version: 2010-12-9 下午09:30:16
	  * @See:
	 */
	List<UserInfo> findCertficateUser(Map params) ;
	
	/**
	 * 是否是受限的交易查询 用户
	 * @param userId
	 * @return
	 */
	boolean isUserOfLimitedTransQuery(String userId);


	
}