package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.DepositBatRegDAO;
import gnete.card.entity.DepositBatReg;
import gnete.card.entity.DepositReg;

@Repository
public class DepositBatRegDAOImpl extends BaseDAOIbatisImpl implements DepositBatRegDAO {

    public String getNamespace() {
        return "DepositBatReg";
    }
    
	public Paginater findDepositReg(Map<String, Object> params, int pageNumber,
			int pageSize){
    	return this.queryForPage("findDepositBatReg", params, pageNumber, pageSize);
    }
	
	// 查询批量充值登记簿列表
	public List<DepositReg> findDepositReg(Map<String, Object> params){
		return queryForList("findDepositBatReg", params);		
	}
	
	// 查询批量充值明细登记簿列表
	public List<DepositBatReg> findDepositBatReg(Map<String, Object> params){
		return queryForList("findDepositBatRegDetail", params);		
	}
	
	public Paginater findDepositBatRegByPage(Map<String, Object> params, int pageNumber,
			int pageSize) {
		return this.queryForPage("findDepositBatRegDetail", params, pageNumber, pageSize);
	}
	
	public int updateStatusByBatchId(Map<String, Object> params) {
		return this.update("updateStatusByBatchId", params);
	}
}