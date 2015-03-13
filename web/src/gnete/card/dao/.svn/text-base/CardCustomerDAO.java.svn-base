package gnete.card.dao;

import java.util.List;
import java.util.Map;

import flink.util.Paginater;
import gnete.card.entity.CardCustomer;

public interface CardCustomerDAO extends BaseDAO {
	
	/**
	 * 查询购卡客户
	 * 
	 * @param params	查询参数信息
	 * 包括:
	 * <ul>
	 * 	<li>cardCustomerId:String 购卡客户ID</li>
	 * 	<li>cardCustomerName:String 购卡客户名称</li>
	 * 	<li>rebateType:String 返利方式</li>
	 * 	<li>state:String 状态</li>
	 * </ul>
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater findCardCustomer(Map<String, Object> params, int pageNumber,
			int pageSize);

	// 查询购卡客户列表
	List<CardCustomer> findCardCustomer(Map<String, Object> params);

}