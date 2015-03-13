package gnete.card.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.Paginater;
import gnete.card.dao.StatisticsCardInfoDAO;
import gnete.card.entity.StatisticsCardBjInfo;
import gnete.card.entity.StatisticsCardInfo;

import org.springframework.stereotype.Repository;

@Repository
public class StatisticsCardInfoDAOImpl extends BaseDAOIbatisImpl implements StatisticsCardInfoDAO {

	public String getNamespace() {
		return "StaticDataQry";
	}

	@Override
	public Paginater findStatisticsCardInfo(
			StatisticsCardInfo statisticsCardInfo, int pageNumber, int pageSize) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("record", statisticsCardInfo);
		return this.queryForPage("listJzdStatisticsCardInfo", params, pageNumber, pageSize);
	}

	@SuppressWarnings("unchecked")
	public List<StatisticsCardInfo> findCardInfoList(
			StatisticsCardInfo statisticsCardInfo) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("record", statisticsCardInfo);
		return this.queryForList("listJzdStatisticsCardInfo", params);
	}

	@SuppressWarnings("unchecked")
	public List<StatisticsCardBjInfo> findCardBjInfoList(
			StatisticsCardBjInfo statisticsCardBjInfo) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("record", statisticsCardBjInfo);
		return this.queryForList("listBjStatusInfo", params);
	}

	public Paginater findStatisticsCardBjInfo(
			StatisticsCardBjInfo statisticsCardBjInfo, int pageNumber,int pageSize) {
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("record", statisticsCardBjInfo);
		return this.queryForPage("listBjStatusInfo", params, pageNumber, pageSize);
	}
}