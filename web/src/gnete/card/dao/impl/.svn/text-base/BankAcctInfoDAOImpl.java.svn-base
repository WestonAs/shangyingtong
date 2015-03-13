package gnete.card.dao.impl;

import flink.util.Paginater;
import gnete.card.dao.BankAcctInfoDAO;
import gnete.card.entity.BankAcct;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
@Repository
public class BankAcctInfoDAOImpl extends BaseDAOIbatisImpl implements BankAcctInfoDAO {

	@Override
	protected String getNamespace() {
		
		return "BankAcct";
	}

	@Override
	public Paginater findPaginater(Map<String, Object> params, int pageNumber, int pageSize) {
		return queryForPage("findBankAcct", params, pageNumber, pageSize);
	}

	@Override
	public List<BankAcct> findBankAccts(Map<String, Object> params) {
		return queryForList("findBankAcct", params);
	}

}
