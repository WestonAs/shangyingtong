package gnete.card.dao.impl;

import flink.util.Paginater;
import gnete.card.dao.CardPrivilegeGroupDAO;

import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class CardPrivilegeGroupDAOImpl extends BaseDAOIbatisImpl implements CardPrivilegeGroupDAO {

    public String getNamespace() {
        return "CardPrivilegeGroup";
    }

	public Paginater find(Map<String, Object> params, int pageNumber,
			int pageSize) {
		return this.queryForPage("find", params, pageNumber, pageSize);
	}
	
}