package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.WxWithdrawInfoDAO;
import gnete.card.entity.WxWithdrawInfo;

@Repository
public class WxWithdrawInfoDAOImpl extends BaseDAOIbatisImpl implements WxWithdrawInfoDAO {

    public String getNamespace() {
        return "WxWithdrawInfo";
    }
    
    @Override
    public Paginater findPage(Map<String, Object> params, int pageNumber, int pageSize) {
    	return super.queryForPage("findPage", params, pageNumber, pageSize);
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public List<WxWithdrawInfo> findList(Map<String, Object> params) {
    	return super.queryForList("findPage", params);
    }
}