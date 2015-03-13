package gnete.card.dao.impl;

import flink.util.Paginater;
import gnete.card.dao.TransCenterMothDAO;
import gnete.card.entity.TransCenterMoth;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class TransCenterMothDAOImpl extends BaseDAOIbatisImpl implements TransCenterMothDAO {

	public String getNamespace() {
		return "StaticDataQry";
	}

	@Override
	public Paginater findTransCenterMoth(Map<String,Object> params,
			int pageNumber, int pageSize) {
		return this.queryForPage("listTransCenterMoth", params, pageNumber, pageSize);
	}

	@SuppressWarnings("unchecked")
	public List<TransCenterMoth> findTransCenterMoth(
			Map<String,Object> params) {
		return this.queryForList("listTransCenterMoth", params);
	}


}