package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.WxCashierInfoDAO;
import gnete.card.entity.WxCashierInfo;

@Repository
public class WxCashierInfoDAOImpl extends BaseDAOIbatisImpl implements WxCashierInfoDAO {

    public String getNamespace() {
        return "WxCashierInfo";
    }
    
    @Override
    public Paginater findPage(Map<String, Object> params, int pageNumber, int pageSize) {
    	return super.queryForPage("findPage", params, pageNumber, pageSize);
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public List<WxCashierInfo> findList(Map<String, Object> params) {
    	return super.queryForList("findPage", params);
    }
}