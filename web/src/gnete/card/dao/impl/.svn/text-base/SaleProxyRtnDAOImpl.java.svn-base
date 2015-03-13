package gnete.card.dao.impl;

import java.util.Map;

import flink.util.Paginater;
import gnete.card.dao.SaleProxyRtnDAO;

import org.springframework.stereotype.Repository;
@Repository
public class SaleProxyRtnDAOImpl extends BaseDAOIbatisImpl implements SaleProxyRtnDAO {

    public String getNamespace() {
        return "SaleProxyRtn";
    }
    
    public Paginater findSaleProxyRtn(Map param, int pageNumber,
    		int pageSize){
    	return this.queryForPage("findSaleProxyRtn", param, pageNumber, pageSize);
    }
}