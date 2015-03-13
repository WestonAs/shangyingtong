package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.WxUserInfoDAO;
import gnete.card.entity.WxUserInfo;

@Repository
public class WxUserInfoDAOImpl extends BaseDAOIbatisImpl implements WxUserInfoDAO {

    public String getNamespace() {
        return "WxUserInfo";
    }
    
    @Override
    public Paginater findPage(Map<String, Object> params, int pageNumber, int pageSize) {
    	return super.queryForPage("findPage", params, pageNumber, pageSize);
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public List<WxUserInfo> findList(Map<String, Object> params) {
    	return super.queryForList("findPage", params);
    }
    
    @Override
    public WxUserInfo findCardIdByPk(String userId) {
    	return (WxUserInfo) super.queryForObject("findCardIdByPk", userId);
    }
}