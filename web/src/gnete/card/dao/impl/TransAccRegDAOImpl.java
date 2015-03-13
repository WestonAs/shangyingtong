package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.TransAccRegDAO;
import gnete.card.entity.TransAccReg;

@Repository
public class TransAccRegDAOImpl extends BaseDAOIbatisImpl implements TransAccRegDAO {

    public String getNamespace() {
        return "TransAccReg";
    }
    
	public Paginater findTransAccReg(Map<String, Object> params, int pageNumber,
			int pageSize){
    	return this.queryForPage("findTransAccReg", params, pageNumber, pageSize);
    }
	
	// 查询卡转账登记簿列表
	public List<TransAccReg> findTransAccReg(Map<String, Object> params){
		return queryForList("findTransAccReg", params);		
	}
}