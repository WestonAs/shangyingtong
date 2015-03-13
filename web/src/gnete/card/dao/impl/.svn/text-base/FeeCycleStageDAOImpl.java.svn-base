package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.FeeCycleStageDAO;
import gnete.card.entity.FeeCycleStage;

@Repository
public class FeeCycleStageDAOImpl extends BaseDAOIbatisImpl implements FeeCycleStageDAO {

    public String getNamespace() {
		return "FeeCycleStage";
	}

	public Paginater findFeeCycleStage(Map<String, Object> parma,
			int pageNumber, int pageSize) {
		return this.queryForPage("findFeeCycleStage", parma, pageNumber, pageSize);
	}

	public List<FeeCycleStage> getFeeCycleStageList(Map<String, Object> params) {
		return this.queryForList("getFeeCycleStageList", params);
	}
}