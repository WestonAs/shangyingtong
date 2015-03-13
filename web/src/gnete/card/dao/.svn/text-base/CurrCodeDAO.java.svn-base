package gnete.card.dao;

import flink.util.Paginater;
import gnete.card.entity.CurrCode;

import java.util.List;
import java.util.Map;

public interface CurrCodeDAO extends BaseDAO {
	
	/**
	 * 查找状态有效的货币类型
	 * 
	 * @param status 状态
	 * @return
	 */
	List<CurrCode> findCurrCode(String status);

	Paginater find(Map<String, Object> params, int pageNumber, int pageSize);
}