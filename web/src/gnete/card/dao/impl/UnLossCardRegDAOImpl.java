package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.UnLossCardRegDAO;
import gnete.card.entity.CardInfo;

@Repository
public class UnLossCardRegDAOImpl extends BaseDAOIbatisImpl implements UnLossCardRegDAO {

    public String getNamespace() {
        return "unlossCardReg";
    }

	public Paginater findUnLossCard(Map<String, Object> params, int pageNumber,
			int pageSize) {
		return this.queryForPage("findUnLossCard", params, pageNumber, pageSize);
	}

	public List<CardInfo> findCardInfo(Map<String, Object> params) {

		return this.queryForList("findCardInfo", params);
	}

	public int updateCardInfo(CardInfo cardInfo) {
		return this.update("updateCardInfo", cardInfo);
	}

	public Paginater findUnLossCardBat(Map<String, Object> params,
			int pageNumber, int pageSize) {
		return this.queryForPage("findUnLossCardBat", params, pageNumber, pageSize);
	}
}