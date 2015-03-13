package gnete.card.dao.impl;

import flink.util.Paginater;
import gnete.card.dao.CardSmsRecordDAO;

import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class CardSmsRecordDAOImpl extends BaseDAOIbatisImpl implements CardSmsRecordDAO {

	public String getNamespace() {
		return "CardSmsRecord";
	}

	@Override
	public Paginater findByPage(Map<String, Object> params, int pageNumber, int pageSize) {
		return this.queryForPage("findByPage", params, pageNumber, pageSize);
	}

	@Override
	public boolean isAuthorizedBranch(String cardBranchCode) {
		Integer ret = (Integer) this.queryForObject("isAuthorizedBranch", cardBranchCode);
		return (ret != null && ret > 0) ? true : false;
	}
}