package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.SignRuleRegDAO;
import gnete.card.entity.SignRuleReg;

@Repository
public class SignRuleRegDAOImpl extends BaseDAOIbatisImpl implements
		SignRuleRegDAO {

	public String getNamespace() {
		return "SignRuleReg";
	}

	public Paginater findSignRuleReg(Map<String, Object> params,
			int pageNumber, int pageSize) {
		return this.queryForPage("findSignRuleReg", params, pageNumber,
				pageSize);
	}

	public List<SignRuleReg> findSignRuleReg(Map<String, Object> params) {
		return queryForList("findSignRuleReg", params);
	}

	public List<SignRuleReg> findSignRuleByCust(String signCustId) {
		return queryForList("findSignRuleByCust", signCustId);
	}

}