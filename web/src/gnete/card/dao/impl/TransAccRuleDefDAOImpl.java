package gnete.card.dao.impl;

import java.util.Map;
import org.springframework.stereotype.Repository;
import flink.util.Paginater;
import gnete.card.dao.TransAccRuleDefDAO;

@Repository
public class TransAccRuleDefDAOImpl extends BaseDAOIbatisImpl implements TransAccRuleDefDAO {

    public String getNamespace() {
        return "TransAccRuleDef";
    }

	public Paginater findTransAccRuleDef(Map<String, Object> params,
			int pageNumber, int pageSize) {
		return this.queryForPage("findTransAccRuleDef", params, pageNumber, pageSize);
	}
}