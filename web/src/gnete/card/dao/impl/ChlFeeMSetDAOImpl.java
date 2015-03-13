package gnete.card.dao.impl;

import java.util.Map;
import org.springframework.stereotype.Repository;
import flink.util.Paginater;
import gnete.card.dao.ChlFeeMSetDAO;

@Repository
public class ChlFeeMSetDAOImpl extends BaseDAOIbatisImpl implements ChlFeeMSetDAO {

    public String getNamespace() {
        return "ChlFeeMSet";
    }

	public Paginater findChlFeeMSet(Map<String, Object> params, int pageNumber,
			int pageSize) {
		return this.queryForPage("findChlFeeMSet", params, pageNumber, pageSize);
	}
}