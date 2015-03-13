package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.PointConsmRuleDefDAO;
import gnete.card.entity.PointConsmRuleDef;

@Repository
public class PointConsmRuleDefDAOImpl extends BaseDAOIbatisImpl implements PointConsmRuleDefDAO {

    public String getNamespace() {
        return "PointConsmRuleDef";
    }

	public Paginater findPointConsmRule(Map<String, Object> params,
			int pageNumber, int pageSize) {
		return this.queryForPage("findPointConsmRule", params, pageNumber, pageSize);
	}

	public List<PointConsmRuleDef> findPointConsmRuleAval(Map<String, Object> params) {
		return this.queryForList("findPointConsmRuleAval", params);
	}

	public List<PointConsmRuleDef> getCouponAvalList(Map<String, Object> params) {
		return this.queryForList("getCouponAvalList", params);
	}

	public List<PointConsmRuleDef> getPointConsmRuleByClass(Map<String, Object> params) {
		return this.queryForList("getPointConsmRuleByClass", params);
	}
}