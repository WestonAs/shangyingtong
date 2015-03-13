package gnete.card.dao.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.CommonHelper;
import flink.util.Paginater;
import gnete.card.dao.CostRecordDAO;
import gnete.card.entity.CostRecord;
import gnete.card.entity.type.CostRecordType;

@Repository
public class CostRecordDAOImpl extends BaseDAOIbatisImpl implements CostRecordDAO {

    public String getNamespace() {
        return "CostRecord";
    }
    
    public Paginater findPage(Map<String, Object> params, int pageNumber,
    		int pageSize) {
    	return this.queryForPage("findPage", params, pageNumber, pageSize);
    }
    
    public List<CostRecord> findList(Map<String, Object> params) {
    	return this.queryForList("findPage", params);
    }
    
    public Map findClear2PayPlanMap(String rmaDate, String keyProperty) {
    	if (CommonHelper.checkIsEmpty(rmaDate) || CommonHelper.checkIsEmpty(keyProperty)) {
			return Collections.EMPTY_MAP;
		}
    	Map<String, Object> params = new HashMap<String, Object>();
    	
    	params.put("param", rmaDate);
    	params.put("type", CostRecordType.PLAN_AMT.getValue());
    	
    	return this.getSqlRunner().queryForMap(getStatementName("findClear2PayMap"), params, keyProperty.toUpperCase());
    }
    
    public Map findClear2PayMakeCardMap(String rmaDate, String keyProperty) {
    	if (CommonHelper.checkIsEmpty(rmaDate) || CommonHelper.checkIsEmpty(keyProperty)) {
			return Collections.EMPTY_MAP;
		}
    	
    	Map<String, Object> params = new HashMap<String, Object>();
    	
    	params.put("param", rmaDate);
    	params.put("type", CostRecordType.MAKE_AMT.getValue());
    	
    	return this.getSqlRunner().queryForMap(getStatementName("findClear2PayMap"), params, keyProperty.toUpperCase());
    }
    
    public boolean updateCostRmaFileBatch(List<CostRecord> costRecordList) {
    	this.updateBatch("updateRmaFile", costRecordList);
    	return true;
    }
}