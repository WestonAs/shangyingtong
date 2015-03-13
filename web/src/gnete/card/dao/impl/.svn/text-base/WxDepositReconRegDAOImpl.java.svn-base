package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.WxDepositReconRegDAO;
import gnete.card.entity.WxDepositReconReg;

@Repository
public class WxDepositReconRegDAOImpl extends BaseDAOIbatisImpl implements WxDepositReconRegDAO {

    public String getNamespace() {
        return "WxDepositReconReg";
    }
    
    @Override
    public Paginater findPage(Map<String, Object> params, int pageNumber, int pageSize) {
    	return super.queryForPage("findPage", params, pageNumber, pageSize);
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public List<WxDepositReconReg> findList(Map<String, Object> params) {
    	return super.queryForList("findPage", params);
    }
}