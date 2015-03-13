package gnete.card.dao.impl;

import java.util.Map;
import org.springframework.stereotype.Repository;
import flink.util.Paginater;
import gnete.card.dao.RmaFileParaDAO;

@Repository
public class RmaFileParaDAOImpl extends BaseDAOIbatisImpl implements RmaFileParaDAO {

    public String getNamespace() {
        return "RmaFilePara";
    }

	public Paginater findRmaFilePara(Map<String, Object> params,
			int pageNumber, int pageSize) {
		return this.queryForPage("findRmaFilePara", params, pageNumber, pageSize);
	}
}