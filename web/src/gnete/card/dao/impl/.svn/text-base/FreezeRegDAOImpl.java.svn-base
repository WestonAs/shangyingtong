package gnete.card.dao.impl;

import flink.util.Paginater;
import gnete.card.dao.FreezeRegDAO;
import gnete.card.entity.SubAcctBal;

import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class FreezeRegDAOImpl extends BaseDAOIbatisImpl implements FreezeRegDAO {

    public String getNamespace() {
        return "FreezeReg";
    }

	public Paginater findFreezeWithMultiParms(Map<String, Object> params,
			int pageNumber, int pageSize) {
		return this.queryForPage("findFreezeWithMultiParms",  params, pageNumber, pageSize);
	}

	public Paginater findCardInfo(Map<String, Object> params, int pageNumber,
			int pageSize) {
		return this.queryForPage("findCardInfo", params, pageNumber, pageSize);
	}
	
	public Object findCardInfo(Map<String, Object> params){
		return this.queryForObject("findCardInfo", params);
	}

	public SubAcctBal findAmt(Map<String, Object> params) {
		return (SubAcctBal)this.queryForObject("findAmt", params);
	}

	public Paginater findFreezeBat(Map<String, Object> params, int pageNumber,
			int pageSize) {
		return this.queryForPage("findFreezeBat", params, pageNumber, pageSize);
	}
}