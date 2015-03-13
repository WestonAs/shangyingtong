package gnete.card.dao.impl;

import java.util.Map;
import org.springframework.stereotype.Repository;
import flink.util.Paginater;
import gnete.card.dao.CardRiskRegDAO;

@Repository
public class CardRiskRegDAOImpl extends BaseDAOIbatisImpl implements CardRiskRegDAO {

    public String getNamespace() {
        return "CardRiskReg";
    }

	public Paginater findCardRiskReg(Map<String, Object> params,
			int pageNumber, int pageSize) {
		return this.queryForPage("findCardRiskReg", params, pageNumber, pageSize);
	}
}