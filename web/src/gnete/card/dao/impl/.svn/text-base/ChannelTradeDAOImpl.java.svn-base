package gnete.card.dao.impl;

import flink.util.Paginater;
import gnete.card.dao.ChannelTradeDAO;
import gnete.card.entity.ChannelTrade;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
@Repository
public class ChannelTradeDAOImpl extends BaseDAOIbatisImpl implements ChannelTradeDAO {

    public String getNamespace() {
        return "ChannelTrade";
    }

	@Override
	public Paginater findPaginater(Map<String, Object> params, int pageNumber, int pageSize) {
		return super.queryForPage("findChannelTradeInfo", params, pageNumber, pageSize);
	}

	@Override
	public List<ChannelTrade> findChannelTrades(Map<String, Object> params) {
		return super.queryForList("findChannelTradeInfo", params);
	}
}