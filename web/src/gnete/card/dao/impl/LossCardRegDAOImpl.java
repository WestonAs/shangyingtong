package gnete.card.dao.impl;

import flink.util.Paginater;
import gnete.card.dao.LossCardRegDAO;
import gnete.card.entity.CardExtraInfo;
import gnete.card.entity.CardInfo;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class LossCardRegDAOImpl extends BaseDAOIbatisImpl implements LossCardRegDAO {

    public String getNamespace() {
        return "lossCardReg";
    }

	public Paginater findLossCard(Map<String, Object> params, int pageNumber,
			int pageSize) {
		return this.queryForPage("findLossCard", params, pageNumber, pageSize);
	}

	public List<CardExtraInfo> findCardExtraInfo(Map<String, Object> params) {
		return this.queryForList("findCardExtraInfo", params);
	}

	public List<CardInfo> findCardInfo(Map<String, Object> params) {
		return this.queryForList("findCardInfo", params);
	}
}