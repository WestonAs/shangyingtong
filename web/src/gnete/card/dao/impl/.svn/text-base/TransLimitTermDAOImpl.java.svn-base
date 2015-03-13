package gnete.card.dao.impl;

import flink.util.Paginater;
import gnete.card.dao.TransLimitTermDAO;

import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class TransLimitTermDAOImpl extends BaseDAOIbatisImpl implements TransLimitTermDAO {

	@Override
	public String getNamespace() {
		return "TransLimitTerm";
	}

	@Override
	public Paginater findTransLimitTerm(Map<String, Object> params, int pageNumber, int pageSize) {
		return this.queryForPage("findTransLimitTerm", params, pageNumber, pageSize);
	}
}