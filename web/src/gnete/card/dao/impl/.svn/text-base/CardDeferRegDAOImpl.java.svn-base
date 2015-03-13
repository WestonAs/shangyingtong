package gnete.card.dao.impl;

import flink.util.Paginater;
import gnete.card.dao.CardDeferRegDAO;
import gnete.card.entity.CardDeferReg;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
@Repository
public class CardDeferRegDAOImpl extends BaseDAOIbatisImpl implements CardDeferRegDAO {

    public String getNamespace() {
        return "CardDeferReg";
    }
    
//    public Paginater findCardDeferWithMultiParms(Map<String, Object> params,
//			int pageNumber, int pageSize) {
//		return this.queryForPage("findCardDeferWithMultiParms", params, pageNumber, pageSize);
//	}

//    public Paginater findCardDeferBat(Map<String, Object> params,
//    		int pageNumber, int pageSize) {
//    	return this.queryForPage("findCardDeferBat", params, pageNumber, pageSize);
//    }
    
    public Paginater findCardDeferPage(Map<String, Object> params,
    		int pageNumber, int pageSize) {
    	return this.queryForPage("findCardDeferPage", params, pageNumber, pageSize);
    }
    
    public List<CardDeferReg> findCardDeferList(Map<String, Object> params) {
    	return this.queryForList("findCardDeferPage", params);
    }
    
    public List<CardDeferReg> findCardDeferCheckList(Map<String, Object> params) {
    	return this.queryForList("findCardDeferCheckPage", params);
    }
}