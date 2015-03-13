package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.WxPayDepositInfoDAO;
import gnete.card.entity.WxPayDepositInfo;

@Repository
public class WxPayDepositInfoDAOImpl extends BaseDAOIbatisImpl implements WxPayDepositInfoDAO {

    public String getNamespace() {
        return "WxPayDepositInfo";
    }
    
    @Override
    public Paginater findPage(Map<String, Object> params, int pageNumber, int pageSize) {
    	return super.queryForPage("findPage", params, pageNumber, pageSize);
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public List<WxPayDepositInfo> findList(Map<String, Object> params) {
    	return super.queryForList("findPage", params);
    }
}