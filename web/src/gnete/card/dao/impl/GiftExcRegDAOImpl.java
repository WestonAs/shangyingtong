package gnete.card.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.GiftExcRegDAO;

@Repository
public class GiftExcRegDAOImpl extends BaseDAOIbatisImpl implements GiftExcRegDAO {

    public String getNamespace() {
        return "GiftExcReg";
    }

	public Paginater findGiftExcReg(Map<String, Object> params, int pageNumber,
			int pageSize) {
		return this.queryForPage("findGiftExcReg", params, pageNumber, pageSize);
	}

}