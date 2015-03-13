package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.DepositRegDAO;
import gnete.card.entity.DepositReg;

@Repository
public class DepositRegDAOImpl extends BaseDAOIbatisImpl implements DepositRegDAO {

    public String getNamespace() {
        return "DepositReg";
    }
	public Paginater findDepositReg(Map<String, Object> params, int pageNumber,
			int pageSize){
    	return this.queryForPage("findDepositReg", params, pageNumber, pageSize);
    }
	
	public Paginater findDepositRegSign(Map<String, Object> params, int pageNumber,
			int pageSize){
    	return this.queryForPage("findDepositRegSign", params, pageNumber, pageSize);
    }
	
	// 查询充值登记簿列表
	public List<DepositReg> findDepositReg(Map<String, Object> params){
		return queryForList("findDepositReg", params);		
	}
	
	// 查询充值登记簿(签单卡)列表
	public List<DepositReg> findDepositRegSign(Map<String, Object> params){
		return queryForList("findDepositRegSign", params);		
	}
	
	public Paginater findDepositRegCancel(Map<String, Object> params,
			int pageNumber, int pageSize) {
		return this.queryForPage("findDepositRegCancel", params, pageNumber, pageSize);
	}
}