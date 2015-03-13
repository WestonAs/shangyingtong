package gnete.card.dao.impl;

import flink.util.Paginater;
import gnete.card.dao.CardTypeCodeDAO;
import gnete.card.entity.CardTypeCode;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class CardTypeCodeDAOImpl extends BaseDAOIbatisImpl implements CardTypeCodeDAO {

	public String getNamespace() {
		return "CardTypeCode";
	}

	public List<CardTypeCode> findCardTypeCode(String status) {
		return this.queryForList("findCardTypeCode", status);
	}
	
	public Paginater find(Map<String, Object> params, int pageNumber,
    		int pageSize) {
		return this.queryForPage("find", params,pageNumber,pageSize);
	}
}