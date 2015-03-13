package gnete.card.dao.impl;

import java.util.Map;
import org.springframework.stereotype.Repository;
import flink.util.Paginater;
import gnete.card.dao.CardIssuerFeeDTotalDAO;

@Repository
public class CardIssuerFeeDTotalDAOImpl extends BaseDAOIbatisImpl implements CardIssuerFeeDTotalDAO {

    public String getNamespace() {
        return "CardIssuerFeeDTotal";
    }

	public Paginater findCardIssuerFeeDTotal(Map<String, Object> params,
			int pageNumber, int pageSize) {
		return this.queryForPage("findCardIssuerFeeDTotal", params, pageNumber, pageSize);
	}
}