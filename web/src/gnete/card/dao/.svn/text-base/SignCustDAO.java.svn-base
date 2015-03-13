package gnete.card.dao;

import java.util.List;
import java.util.Map;

import flink.util.Paginater;
import gnete.card.entity.SignCust;

public interface SignCustDAO extends BaseDAO {
	/**
	 * 分页查询签单卡客户
	 * 
	 * @param params	查询参数信息
	 * 包括:
	 * <ul>
	 * 	<li>signCustId:String 购卡客户ID</li>
	 * 	<li>signCustName:String 购卡客户名称</li>
	 * 	<li>state:String 状态</li>
	 * </ul>
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater findSignCust(Map<String, Object> params, int pageNumber,
			int pageSize);

	// 查询签单卡客户列表
	List<SignCust> findSignCust(Map<String, Object> params);

}