package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import flink.util.Paginater;
import gnete.card.dao.GiftDefDAO;
import gnete.card.entity.GiftDef;

@Repository
public class GiftDefDAOImpl extends BaseDAOIbatisImpl implements GiftDefDAO {

    public String getNamespace() {
		return "GiftDef";
	}

	public Paginater findGift(Map<String, Object> params, int pageNumber,
			int pageSize) {
	
		return this.queryForPage("findGift", params, pageNumber, pageSize);
	}

	public Paginater findAvalGift(Map<String, Object> params, int pageNumber,
			int pageSize) {
		return this.queryForPage("findAvalGift", params, pageNumber, pageSize);
	}

	public List<GiftDef> findGiftByPtClass(Map<String, Object> params) {
		return this.queryForList("findGiftByPtClass", params);
	}
}