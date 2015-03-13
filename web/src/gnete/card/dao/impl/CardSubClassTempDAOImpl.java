package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.CardSubClassTempDAO;
import gnete.card.entity.CardSubClassTemp;
@Repository
public class CardSubClassTempDAOImpl extends BaseDAOIbatisImpl implements CardSubClassTempDAO {

    public String getNamespace() {
        return "CardSubClassTemp";
    }

	public Paginater findSubClassTemp(Map<String, Object> params, int pageNumber, int pageSize) {
		return this.queryForPage("findCardSubClassTemp", params, pageNumber, pageSize);
	}

	public List<CardSubClassTemp> findList(Map<String, Object> params) {
		return this.queryForList("findCardSubClassTemp", params);
	}
	
	public boolean isExsitCardSubClassName(String cardSubClassName) {
		return (Long) this.queryForObject("isExsitCardSubClassName", cardSubClassName) > 0;
	}
}