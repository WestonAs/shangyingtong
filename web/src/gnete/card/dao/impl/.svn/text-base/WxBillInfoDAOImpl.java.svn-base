package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.WxBillInfoDAO;
import gnete.card.entity.WxBillInfo;

@Repository
public class WxBillInfoDAOImpl extends BaseDAOIbatisImpl implements WxBillInfoDAO {

    public String getNamespace() {
        return "WxBillInfo";
    }
    
    @Override
    public Paginater findPage(Map<String, Object> params, int pageNumber, int pageSize) {
    	return super.queryForPage("findPage", params, pageNumber, pageSize);
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public List<WxBillInfo> findList(Map<String, Object> params) {
    	return super.queryForList("findPage", params);
    }
}