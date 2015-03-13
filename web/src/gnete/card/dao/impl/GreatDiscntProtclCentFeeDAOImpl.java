package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.GreatDiscntProtclCentFeeDAO;
import gnete.card.entity.GreatDiscntProtclCentFee;

@Repository
public class GreatDiscntProtclCentFeeDAOImpl extends BaseDAOIbatisImpl implements GreatDiscntProtclCentFeeDAO {

	public String getNamespace() {
		return "GreatDiscntProtclCentFee";
	}

	public Paginater findPage(Map<String, Object> params, int pageNumber, int pageSize) {
		return this.queryForPage("findGreatDiscntProtclCentFee", params, pageNumber, pageSize);
	}
	
	public List<GreatDiscntProtclCentFee> findList(Map<String, Object> params) {
		return this.queryForList("findGreatDiscntProtclCentFee", params);
	}
}