package gnete.card.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.PlanDefDAO;
import gnete.card.entity.PlanDef;

@Repository
public class PlanDefDAOImpl extends BaseDAOIbatisImpl implements PlanDefDAO {

    public String getNamespace() {
        return "PlanDef";
    }
    
    public Paginater findPage(Map<String, Object> params, int pageNumber,
    		int pageSize) {
    	return this.queryForPage("findPage", params, pageNumber, pageSize);
    }
    
    public List<PlanDef> findList(Map<String, Object> params) {
    	return this.queryForList("findPage", params);
    }
    
    public boolean isExsitModelName(String modelName, String branchCode) {
    	Map<String, Object> params = new HashMap<String, Object>();
    	
    	params.put("planName", modelName);
    	params.put("branchCode", branchCode);
    	return (Long) super.queryForObject("findModelName", params) > 0;
    }
}