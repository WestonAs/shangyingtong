package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.CardCustomerDAO;
import gnete.card.entity.CardCustomer;

@Repository
public class CardCustomerDAOImpl extends BaseDAOIbatisImpl implements CardCustomerDAO {

    public String getNamespace() {
		return "CardCustomer";
	}

	public Paginater findCardCustomer(Map<String, Object> params, int pageNumber,
			int pageSize){
    	return this.queryForPage("findCardCustomer", params, pageNumber, pageSize);
    }
	
	// 查询购卡客户列表
	public List<CardCustomer> findCardCustomer(Map<String, Object> params){
		return queryForList("findCardCustomer", params);		
	}
}