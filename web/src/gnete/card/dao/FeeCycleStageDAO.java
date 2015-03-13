package gnete.card.dao;

import java.util.List;
import java.util.Map;
import flink.util.Paginater;
import gnete.card.entity.FeeCycleStage;

public interface FeeCycleStageDAO extends BaseDAO {
	public Paginater findFeeCycleStage(Map<String, Object> parma, int pageNumber,
    		int pageSize);
	
	List<FeeCycleStage> getFeeCycleStageList(Map<String, Object> params);
}