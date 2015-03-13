package gnete.card.dao.impl;

import flink.util.Paginater;
import gnete.card.dao.SaleCardBatRegDAO;
import gnete.card.entity.SaleCardBatReg;
import gnete.card.entity.SaleCardReg;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class SaleCardBatRegDAOImpl extends BaseDAOIbatisImpl implements SaleCardBatRegDAO {

    public String getNamespace() {
        return "SaleCardBatReg";
    }
	public Paginater findSaleCardReg(Map<String, Object> params, int pageNumber,
			int pageSize){
    	return this.queryForPage("findSaleCardBatReg", params, pageNumber, pageSize);
    }
	
	// 查询批量售卡登记簿列表
	public List<SaleCardReg> findSaleCardReg(Map<String, Object> params){
		return queryForList("findSaleCardBatReg", params);		
	}
	
	// 查询批量售卡明细登记簿列表
	public List<SaleCardBatReg> findSaleCardBatReg(Map<String, Object> params){
		return queryForList("findSaleCardBatRegDetail", params);		
	}
	
	public Paginater findSaleCardBatByPage(Map<String, Object> params,
			int pageNumber, int pageSize) {
    	return this.queryForPage("findSaleCardBatRegDetail", params, pageNumber, pageSize);
	}
	
	@Override
	public int updateStatusByBatId(Long saleBatchId, String status) {
		Map<String, Object> batRegMap = new HashMap<String, Object>();
		batRegMap.put("status", status);
		batRegMap.put("saleBatchId", saleBatchId);
		// 更新售卡明细记录的状态
		return this.update("updateStatusByBatId", batRegMap);
	}
	
	@Override
	public List<SaleCardBatReg> findBySaleBatchId(Long saleBatchId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("saleBatchId", saleBatchId);
		return findSaleCardBatReg(params);
	}
}