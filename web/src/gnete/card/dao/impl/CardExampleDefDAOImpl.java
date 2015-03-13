package gnete.card.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.CardExampleDefDAO;
import gnete.card.entity.CardExampleDef;

@Repository
public class CardExampleDefDAOImpl extends BaseDAOIbatisImpl implements CardExampleDefDAO {

    public String getNamespace() {
        return "CardExampleDef";
    }
    
    public Paginater findPage(Map<String, Object> params, int pageNumber,
    		int pageSize) {
    	return this.queryForPage("findPage", params, pageNumber, pageSize);
    }
    
    public Paginater findSelectPage(Map<String, Object> params, int pageNumber,
    		int pageSize) {
    	return this.queryForPage("findSelectPage", params, pageNumber, pageSize);
    }
    
    public List<CardExampleDef> findList(Map<String, Object> params) {
    	return this.queryForList("findPage", params);
    }
    
    public boolean isExsitStyleName(String styleName, String branchCode) {
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("cardExampleName", styleName);
    	params.put("branchCode", branchCode);
    	
    	return (Long) super.queryForObject("findStyleName", params) > 0;
    }
}