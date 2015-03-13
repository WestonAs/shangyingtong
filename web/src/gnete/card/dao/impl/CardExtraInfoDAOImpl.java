package gnete.card.dao.impl;

import flink.util.Paginater;
import gnete.card.dao.CardExtraInfoDAO;
import gnete.card.entity.CardExtraInfo;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class CardExtraInfoDAOImpl extends BaseDAOIbatisImpl implements CardExtraInfoDAO {

    public String getNamespace() {
        return "CardExtraInfo";
    }

	public Paginater findCardExtraInfo(Map<String, Object> params,
			int pageNumber, int pageSize) {
		return this.queryForPage("findCardExtraInfo", params, pageNumber, pageSize);
	}

	public Object findCardExtraInfoByIdName(Map<String, Object> params) {
		return this.queryForObject("findCardExtraInfoByIdName", params);
	}
	
	public List<CardExtraInfo> findCardExtraInfoByParam(Map<String, String> params) {
		return this.queryForList("findCardExtraInfoByParam", params);
	}


}