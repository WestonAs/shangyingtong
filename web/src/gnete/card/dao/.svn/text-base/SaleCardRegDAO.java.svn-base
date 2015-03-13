package gnete.card.dao;

import java.util.List;
import java.util.Map;

import flink.util.Paginater;
import gnete.card.entity.SaleCardReg;

public interface SaleCardRegDAO extends BaseDAO {
	
	/**
	 *  售卡列表页面
	 * @param params
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater findSaleCardReg(Map<String, Object> params, int pageNumber,
			int pageSize);

	/**
	 * 售卡撤销列表页面
	 * @param params
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater findSaleCardCancel(Map<String, Object> params, int pageNumber,
			int pageSize);

	// 查询售卡登记簿列表
	List<SaleCardReg> findSaleCardReg(Map<String, Object> params);

}