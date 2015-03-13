package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.LargessDefDAO;
import gnete.card.entity.LargessDef;
@Repository
public class LargessDefDAOImpl extends BaseDAOIbatisImpl implements LargessDefDAO {

    public String getNamespace() {
        return "LargessDef";
    }
    
    public Paginater findLargessDef(Map<String, Object> params, int pageNumber,
			int pageSize){
    	return this.queryForPage("findLargessDef", params, pageNumber, pageSize);
    }

	public List<LargessDef> findLargessDefByBranch(Map<String, Object> params) {
		return this.queryForList("findLargessDefByBranch", params);
	}
}