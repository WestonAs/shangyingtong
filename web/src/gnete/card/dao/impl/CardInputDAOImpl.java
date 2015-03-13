package gnete.card.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.CardInputDAO;

@Repository
public class CardInputDAOImpl extends BaseDAOIbatisImpl implements CardInputDAO {

	public String getNamespace() {
		return "CardInput";
	}

	public Paginater findCardInputPage(Map<String, Object> params,
			int pageNumber, int pageSize) {
		return this.queryForPage("findCardInputPage", params, pageNumber,
				pageSize);
	}

	public Long isExistCardInput(Map<String, Object> params) {
		return (Long) queryForObject("isExistCardInput", params) ;
	}
}