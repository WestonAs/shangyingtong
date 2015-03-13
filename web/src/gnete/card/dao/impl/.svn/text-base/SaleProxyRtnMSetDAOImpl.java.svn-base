package gnete.card.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.SaleProxyRtnMSetDAO;

@Repository
public class SaleProxyRtnMSetDAOImpl extends BaseDAOIbatisImpl implements SaleProxyRtnMSetDAO {

    public String getNamespace() {
        return "SaleProxyRtnMSet";
    }
    public Paginater findSaleProxyRtnMSet(Map<String, Object> params, int pageNumber,
    		int pageSize) {
    	return this.queryForPage("findSaleProxyRtnMSet", params, pageNumber, pageSize);
    }
}