package gnete.card.dao.impl;

import flink.util.Paginater;
import gnete.card.dao.SubAcctBalDAO;
import gnete.card.entity.SubAcctBal;
import gnete.card.entity.SubAcctBalKey;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class SubAcctBalDAOImpl extends BaseDAOIbatisImpl implements SubAcctBalDAO {

    public String getNamespace() {
        return "SubAcctBal";
    }

	public List<SubAcctBal> findSubAcctBal(Map<String, Object> params)
	{
		return queryForList("findSubAcctBal", params);
	}

	public Paginater findSubAcctBal(Map<String, Object> params, int pageNumber,
			int pageSize) {
		return this.queryForPage("findSubAcctBal", params, pageNumber, pageSize);
	}

	@Override
	public SubAcctBal findByPk(String acctId, String subacctType) {
		return (SubAcctBal) this.findByPk(new SubAcctBalKey(acctId, subacctType));
	}

}