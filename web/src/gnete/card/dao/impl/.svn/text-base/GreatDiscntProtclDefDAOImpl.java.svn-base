package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.GreatDiscntProtclDefDAO;
import gnete.card.entity.GreatDiscntProtclDef;

@Repository
public class GreatDiscntProtclDefDAOImpl extends BaseDAOIbatisImpl implements GreatDiscntProtclDefDAO {

	public String getNamespace() {
		return "GreatDiscntProtclDef";
	}

	public Paginater findPage(Map<String, Object> params, int pageNumber, int pageSize) {
		return this.queryForPage("findGreatDiscntProtclDef", params, pageNumber, pageSize);
	}
	
	public List<GreatDiscntProtclDef> findList(Map<String, Object> params) {
		return this.queryForList("findGreatDiscntProtclDef", params);
	}
}