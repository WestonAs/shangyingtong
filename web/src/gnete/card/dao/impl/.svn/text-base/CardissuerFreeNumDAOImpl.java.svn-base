package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.CardissuerFreeNumDAO;
import gnete.card.entity.CardissuerFreeNum;

@Repository
public class CardissuerFreeNumDAOImpl extends BaseDAOIbatisImpl implements CardissuerFreeNumDAO {

    public String getNamespace() {
        return "CardissuerFreeNum";
    }
    
    public Paginater findPage(Map<String, Object> params, int pageNumber,
    		int pageSize) {
    	return this.queryForPage("findPage", params, pageNumber, pageSize);
    }
    
    public List<CardissuerFreeNum> findList(Map<String, Object> params) {
    	return this.queryForList("findPage", params);
    }
}