package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.RebateRuleDetailDAO;
import gnete.card.entity.RebateRuleDetail;

@Repository
public class RebateRuleDetailDAOImpl extends BaseDAOIbatisImpl implements RebateRuleDetailDAO {

	public String getNamespace() {
        return "RebateRuleDetail";
    }
	public Paginater findRebateRuleDetail(Map<String, Object> params, int pageNumber,
			int pageSize){
    	return this.queryForPage("findRebateRuleDetail", params, pageNumber, pageSize);
    }
	
	// 查询返利规则分段比例明细
	public List<RebateRuleDetail> findRebateRuleDetail(Map<String, Object> params){
		return queryForList("findRebateRuleDetail", params);		
	}
	
	// 删除返利规则分段比例明细
    public int deleteByRebateId(long rebateId) {
    	return this.delete("deleteByRebateId", rebateId);
    }
    
}