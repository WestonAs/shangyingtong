package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.PromtSettCostListDAO;
import gnete.card.entity.PromtSettCostList;

@Repository
public class PromtSettCostListDAOImpl extends BaseDAOIbatisImpl implements PromtSettCostListDAO {

	public String getNamespace() {
		return "PromtSettCostList";
	}

	public Paginater findPage(Map<String, Object> params, int pageNumber, int pageSize) {
		return this.queryForPage("findPromtSettCostList", params, pageNumber, pageSize);
	}
	
	public List<PromtSettCostList> findList(Map<String, Object> params) {
		return this.queryForList("findPromtSettCostList", params);
	}
}