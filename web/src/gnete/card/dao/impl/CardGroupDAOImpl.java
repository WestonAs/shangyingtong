package gnete.card.dao.impl;

import flink.util.Paginater;
import gnete.card.dao.CardGroupDAO;
import gnete.card.entity.CardGroup;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class CardGroupDAOImpl extends BaseDAOIbatisImpl implements CardGroupDAO {

    public String getNamespace() {
        return "CardGroup";
    }
    
    public Paginater findPage(Map<String, Object> params, int pageNumber,
    		int pageSize) {
    	return this.queryForPage("findCardGroup", params, pageNumber, pageSize);
    }

	public List<CardGroup> getBranchList(String groupId) {
		return this.queryForList("getBranchList", groupId);
	}

}