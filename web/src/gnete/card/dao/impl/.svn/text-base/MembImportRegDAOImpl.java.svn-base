package gnete.card.dao.impl;

import java.util.Map;
import org.springframework.stereotype.Repository;
import flink.util.Paginater;
import gnete.card.dao.MembImportRegDAO;

@Repository
public class MembImportRegDAOImpl extends BaseDAOIbatisImpl implements MembImportRegDAO {

    public String getNamespace() {
        return "MembImportReg";
    }

	public Paginater findMembImportReg(Map<String, Object> params,
			int pageNumber, int pageSize) {
		return this.queryForPage("findMembImportReg", params, pageNumber, pageSize);
	}
}