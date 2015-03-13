package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.CardPrevConfigDAO;
import gnete.card.entity.CardPrevConfig;

@Repository
public class CardPrevConfigDAOImpl extends BaseDAOIbatisImpl implements CardPrevConfigDAO {

    public String getNamespace() {
        return "CardPrevConfig";
    }
    
    public Paginater findCardPrevConfigPage(Map<String, Object> params,
    		int pageNumber, int pageSize) {
    	return this.queryForPage("findCardPrevConfigPage", params, pageNumber, pageSize);
    }
    
    public List<CardPrevConfig> findByCardPrev(String cardPrev) {
    	return this.queryForList("findByCardPrev", cardPrev);
    }
}