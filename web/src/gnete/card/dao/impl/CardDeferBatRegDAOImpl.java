package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.CardDeferBatRegDAO;
import gnete.card.entity.CardDeferBatReg;

@Repository
public class CardDeferBatRegDAOImpl extends BaseDAOIbatisImpl implements CardDeferBatRegDAO {

	public String getNamespace() {
		return "CardDeferBatReg";
	}

	public Paginater findPage(Map<String, Object> params, int pageNumber, int pageSize) {
		return this.queryForPage("findCardDeferBatReg", params, pageNumber, pageSize);
	}
	
	public List<CardDeferBatReg> findList(Map<String, Object> params) {
		return this.queryForList("findCardDeferBatReg", params);
	}
}