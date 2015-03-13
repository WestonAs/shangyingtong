package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.CurrCodeDAO;
import gnete.card.entity.CurrCode;

@Repository
public class CurrCodeDAOImpl extends BaseDAOIbatisImpl implements CurrCodeDAO {

	public String getNamespace() {
		return "CurrCode";
	}

	public List<CurrCode> findCurrCode(String status) {
		return this.queryForList("findCurrCode", status);
	}

	public Paginater find(Map<String, Object> params, int pageNumber,
			int pageSize) {
		return this.queryForPage("find", params, pageNumber, pageSize);
	}
}