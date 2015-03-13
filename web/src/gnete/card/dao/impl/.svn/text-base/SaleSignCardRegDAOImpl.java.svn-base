package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.SaleSignCardRegDAO;
import gnete.card.entity.SaleSignCardReg;

@Repository
public class SaleSignCardRegDAOImpl extends BaseDAOIbatisImpl implements SaleSignCardRegDAO {

    public String getNamespace() {
        return "SaleSignCardReg";
    }
	public Paginater findSaleSignCardReg(Map<String, Object> params, int pageNumber,
			int pageSize){
    	return this.queryForPage("findSaleSignCardReg", params, pageNumber, pageSize);
    }
	
	// 查询签单卡列表
	public List<SaleSignCardReg> findSaleSignCardReg(Map<String, Object> params){
		return queryForList("findSaleSignCardReg", params);		
	}
}