package gnete.card.dao.impl;

import flink.util.Paginater;
import gnete.card.dao.WashCarSvcMemberShipDAO;
import gnete.card.entity.WashCarSvcMbShipDues;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class WashCarSvcMemberShipDAOImpl extends BaseDAOIbatisImpl implements WashCarSvcMemberShipDAO {

	@Override
	protected String getNamespace() {
		return "WashCarSvcMbShipDues";
	}

	@Override
	public Paginater findPage(Map<String, Object> params, int pageNumber,
			int pageSize) {
		return this.queryForPage("findPage", params, pageNumber, pageSize);
	}

	@SuppressWarnings("unchecked")
	public List<WashCarSvcMbShipDues> washCarList(String status) {
		
		return this.queryForList("findStatusList", status);
	}

	@Override
	public WashCarSvcMbShipDues findWashCarMb(Map<String, Object> params) {
		return (WashCarSvcMbShipDues) this.queryForObject("findPage", params);
	}
}