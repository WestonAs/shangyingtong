package gnete.card.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.WxReconciliationDetailDAO;

@Repository
public class WxReconciliationDetailDAOImpl extends BaseDAOIbatisImpl implements WxReconciliationDetailDAO {

    public String getNamespace() {
        return "WxReconciliationDetail";
    }
    
    @Override
    public Paginater findPage(Map<String, Object> params, int pageNumber, int pageSize) {
    	return super.queryForPage("findPage", params, pageNumber, pageSize);
    }
}