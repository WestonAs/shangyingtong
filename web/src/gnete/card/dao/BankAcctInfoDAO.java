package gnete.card.dao;

import flink.util.Paginater;
import gnete.card.entity.BankAcct;

import java.util.List;
import java.util.Map;

public interface BankAcctInfoDAO extends BaseDAO {

	public Paginater findPaginater(Map<String, Object> params, int pageNumber,
    		int pageSize) ;
	public List<BankAcct> findBankAccts(Map<String, Object> params);
}
