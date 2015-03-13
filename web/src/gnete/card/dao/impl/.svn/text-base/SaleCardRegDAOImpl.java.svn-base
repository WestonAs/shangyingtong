package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.SaleCardRegDAO;
import gnete.card.entity.SaleCardReg;

@Repository
public class SaleCardRegDAOImpl extends BaseDAOIbatisImpl implements SaleCardRegDAO {

    public String getNamespace() {
        return "SaleCardReg";
    }
	public Paginater findSaleCardReg(Map<String, Object> params, int pageNumber,
			int pageSize){
    	return this.queryForPage("findSaleCardReg", params, pageNumber, pageSize);
    }
	
	public Paginater findSaleCardCancel(Map<String, Object> params,
			int pageNumber, int pageSize) {
		return this.queryForPage("findSaleCardCancel", params, pageNumber, pageSize);
	}
	
	// 查询售卡登记簿列表
	public List<SaleCardReg> findSaleCardReg(Map<String, Object> params){
		return queryForList("findSaleCardReg", params);		
	}
}