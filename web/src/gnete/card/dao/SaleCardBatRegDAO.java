package gnete.card.dao;

import flink.util.Paginater;
import gnete.card.entity.SaleCardBatReg;
import gnete.card.entity.SaleCardReg;

import java.util.List;
import java.util.Map;

public interface SaleCardBatRegDAO extends BaseDAO {
	public Paginater findSaleCardReg(Map<String, Object> params, int pageNumber,
			int pageSize);
	
	// 查询批量售卡登记簿列表
	List<SaleCardReg> findSaleCardReg(Map<String, Object> params);
	
	// 查询批量售卡明细登记簿列表
	List<SaleCardBatReg> findSaleCardBatReg(Map<String, Object> params);

	Paginater findSaleCardBatByPage(Map<String, Object> params, int pageNumber, int pageSize);
	
	/**
	 *  根据售卡批次更新售卡登记明细表的状态
	 */
	public int updateStatusByBatId(Long saleBatchId, String status);

	public List<SaleCardBatReg> findBySaleBatchId(Long saleBatchId);
	
}