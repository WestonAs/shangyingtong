package gnete.card.dao.impl;

import flink.util.Paginater;
import gnete.card.dao.CustomerRebateRegDAO;
import gnete.card.entity.CustomerRebateReg;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class CustomerRebateRegDAOImpl extends BaseDAOIbatisImpl implements CustomerRebateRegDAO {

    public String getNamespace() {
        return "CustomerRebateReg";
    }
    
    public Paginater findCustomerRebateRegList(Map<String, Object> params, int pageNumber,
			int pageSize){
    	return this.queryForPage("findCustomerRebateReg", params, pageNumber, pageSize);
    }
	
    public Paginater findCustomerRebateRegByCardTypeList(Map<String, Object> params, int pageNumber,
			int pageSize){
    	return this.queryForPage("findCustomerRebateRegByCardType", params, pageNumber, pageSize);
    }
	
	// 查询客户返利登记簿列表
	public List<CustomerRebateReg> findCustomerRebateRegList(Map<String, Object> params){
		return queryForList("findCustomerRebateReg", params);		
	}

	// 按卡类型查询客户返利列表
	public List<CustomerRebateReg> findCustomerRebateRegByCardType(Map<String, Object> params){
		return queryForList("findCustomerRebateRegByCardType", params);		
	}

}