package gnete.card.dao.impl;

import java.util.Map;
import org.springframework.stereotype.Repository;
import flink.util.Paginater;
import gnete.card.dao.ChlFeeDTotalDAO;

@Repository
public class ChlFeeDTotalDAOImpl extends BaseDAOIbatisImpl implements ChlFeeDTotalDAO {

    public String getNamespace() {
        return "ChlFeeDTotal";
    }

	public Paginater findChlFeeDTotal(Map<String, Object> params,
			int pageNumber, int pageSize) {
		return this.queryForPage("findChlFeeDTotal", params, pageNumber, pageSize);
	}
}