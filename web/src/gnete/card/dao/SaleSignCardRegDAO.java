package gnete.card.dao;

import flink.util.Paginater;
import gnete.card.entity.SaleSignCardReg;

import java.util.List;
import java.util.Map;

public interface SaleSignCardRegDAO extends BaseDAO {
	/**
	 * 分页查询签单卡
	 * 
	 * @param params	查询参数信息
	 * 包括:
	 * <ul>
	 * </ul>
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater findSaleSignCardReg(Map<String, Object> params, int pageNumber,
			int pageSize);

	// 查询签单卡列表
	List<SaleSignCardReg> findSaleSignCardReg(Map<String, Object> params);

}