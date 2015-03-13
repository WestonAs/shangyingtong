package gnete.card.dao;

import java.util.List;

import flink.util.Paginater;
import gnete.card.entity.StatisticsCardBjInfo;
import gnete.card.entity.StatisticsCardInfo;

public interface StatisticsCardInfoDAO extends BaseDAO{
    
	//吉之岛
	public Paginater findStatisticsCardInfo(StatisticsCardInfo statisticsCardInfo,
			int pageNumber,int pageSize);
	
	public List<StatisticsCardInfo> findCardInfoList(StatisticsCardInfo statisticsCardInfo);
	
	//百佳
	public Paginater findStatisticsCardBjInfo(StatisticsCardBjInfo statisticsCardBjInfo,
			int pageNumber,int pageSize);
	
	public List<StatisticsCardBjInfo> findCardBjInfoList(StatisticsCardBjInfo statisticsCardBjInfo);
}
