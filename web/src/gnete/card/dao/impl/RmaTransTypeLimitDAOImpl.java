package gnete.card.dao.impl;

import java.util.Map;
import org.springframework.stereotype.Repository;
import flink.util.Paginater;
import gnete.card.dao.RmaTransTypeLimitDAO;

@Repository
public class RmaTransTypeLimitDAOImpl extends BaseDAOIbatisImpl implements RmaTransTypeLimitDAO {

    public String getNamespace() {
        return "RmaTransTypeLimit";
    }

	public Paginater findRmaTransTypeLimit(Map<String, Object> params,
			int pageNumber, int pageSize) {
		return this.queryForPage("findRmaTransTypeLimit", params, pageNumber, pageSize);
	}
}