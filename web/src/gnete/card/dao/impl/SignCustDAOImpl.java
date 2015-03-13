package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.SignCustDAO;
import gnete.card.entity.SignCust;

@Repository
public class SignCustDAOImpl extends BaseDAOIbatisImpl implements SignCustDAO {

    public String getNamespace() {
        return "SignCust";
    }
	public Paginater findSignCust(Map<String, Object> params, int pageNumber,
			int pageSize){
    	return this.queryForPage("findSignCust", params, pageNumber, pageSize);
    }
	
	// 查询签单卡客户列表
	public List<SignCust> findSignCust(Map<String, Object> params){
		return queryForList("findSignCust", params);		
	}
}