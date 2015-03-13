package gnete.card.dao.impl;

import java.util.Map;
import org.springframework.stereotype.Repository;
import flink.util.Paginater;
import gnete.card.dao.CpsParaDAO;

@Repository
public class CpsParaDAOImpl extends BaseDAOIbatisImpl implements CpsParaDAO {

    public String getNamespace() {
        return "CpsPara";
    }

    public Paginater findCpsPara(Map<String, Object> params, int pageNumber,
			int pageSize) {
		return this.queryForPage("findCpsPara", params, pageNumber, pageSize);
	}
}