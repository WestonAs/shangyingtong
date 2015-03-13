package gnete.card.dao.impl;

import flink.util.Paginater;
import gnete.card.dao.UnfreezeRegDAO;
import gnete.card.entity.SubAcctBal;

import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class UnfreezeRegDAOImpl extends BaseDAOIbatisImpl implements UnfreezeRegDAO {

    public String getNamespace() {
        return "UnfreezeReg";
    }

	public Paginater findUnfreezeWithMultiParms(Map<String, Object> params,
			int pageNumber, int pageSize) {
		return this.queryForPage("findUnfreezeWithMultiParms",  params, pageNumber, pageSize);
	}

	public Paginater findCardInfo(Map<String, Object> params, int pageNumber,
			int pageSize) {
		return this.queryForPage("findCardInfo", params, pageNumber, pageSize);
	}
	
	public Object findCardInfo(Map<String, Object> params){
		return this.queryForObject("findCardInfo",params);
	}

	public SubAcctBal findAmt(Map<String, Object> params) {
		
		return (SubAcctBal)this.queryForObject("findAmt", params);
	}

	public Paginater findUnfreezeBat(Map<String, Object> params,
			int pageNumber, int pageSize) {
		return this.queryForPage("findUnfreezeBat", params, pageNumber, pageSize);
	}

	
}