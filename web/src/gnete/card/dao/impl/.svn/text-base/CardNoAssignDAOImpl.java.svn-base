package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import gnete.card.dao.CardNoAssignDAO;
import gnete.card.entity.CardNoAssign;

@Repository
public class CardNoAssignDAOImpl extends BaseDAOIbatisImpl implements
		CardNoAssignDAO {

	public String getNamespace() {
		return "CardNoAssign";
	}

	public List<CardNoAssign> findCardNoAssign(Map<String, Object> params) {
		return this.queryForList("findCardNoAssign", params);
	}

	public Long findUseCardNo(Map<String, Object> params) {
		return (Long) this.queryForObject("findUseCardNo", params);
	}

	public Long isExistCardNo(Map<String, Object> params) {
		return (Long) this.queryForObject("isExistCardNo", params);
	}
}