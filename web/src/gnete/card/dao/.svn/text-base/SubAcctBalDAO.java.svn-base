package gnete.card.dao;

import java.util.List;
import java.util.Map;

import flink.util.Paginater;
import gnete.card.entity.SubAcctBal;

public interface SubAcctBalDAO extends BaseDAO {
	public List<SubAcctBal> findSubAcctBal(Map<String, Object> params);
	
	public Paginater findSubAcctBal(Map<String, Object> params, int pageNumber,
			int pageSize);

	public SubAcctBal findByPk(String acctId, String subacctType);
}