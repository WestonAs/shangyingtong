package gnete.card.dao.impl;

import java.util.Map;
import org.springframework.stereotype.Repository;
import flink.util.Paginater;
import gnete.card.dao.InsBankacctDAO;

@Repository
public class InsBankacctDAOImpl extends BaseDAOIbatisImpl implements InsBankacctDAO {

    public String getNamespace() {
        return "InsBankacct";
    }

	public Paginater findInsBankAcct(Map<String, Object> params,
			int pageNumber, int pageSize) {
		return this.queryForPage("findInsBankAcct", params, pageNumber, pageSize);
	}
}