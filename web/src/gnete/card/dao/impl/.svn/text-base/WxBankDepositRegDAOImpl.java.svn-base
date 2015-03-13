package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.WxBankDepositRegDAO;
import gnete.card.entity.WxBankDepositReg;

@Repository
public class WxBankDepositRegDAOImpl extends BaseDAOIbatisImpl implements WxBankDepositRegDAO {

    public String getNamespace() {
        return "WxBankDepositReg";
    }
    
    @Override
    public Paginater findPage(Map<String, Object> params, int pageNumber, int pageSize) {
    	return super.queryForPage("findPage", params, pageNumber, pageSize);
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public List<WxBankDepositReg> findList(Map<String, Object> params) {
    	return super.queryForList("findPage", params);
    }
}