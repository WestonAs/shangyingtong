package gnete.card.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.CardBinPreRegDAO;

@Repository
public class CardBinPreRegDAOImpl extends BaseDAOIbatisImpl implements CardBinPreRegDAO {

    public String getNamespace() {
        return "CardBinPreReg";
    }
    
    public Paginater findCardBinPrexRegPage(Map<String, Object> params,
    		int pageNumber, int pageSize) {
    	return this.queryForPage("findCardBinPrexRegPage", params, pageNumber, pageSize);
    }
}