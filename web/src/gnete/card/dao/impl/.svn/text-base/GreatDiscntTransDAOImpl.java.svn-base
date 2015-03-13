package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.GreatDiscntTransDAO;
import gnete.card.entity.GreatDiscntTrans;

@Repository
public class GreatDiscntTransDAOImpl extends BaseDAOIbatisImpl implements GreatDiscntTransDAO {

	public String getNamespace() {
		return "GreatDiscntTrans";
	}

	public Paginater findPage(Map<String, Object> params, int pageNumber, int pageSize) {
		return this.queryForPage("findGreatDiscntTrans", params, pageNumber, pageSize);
	}
	
	public List<GreatDiscntTrans> findList(Map<String, Object> params) {
		return this.queryForList("findGreatDiscntTrans", params);
	}
}