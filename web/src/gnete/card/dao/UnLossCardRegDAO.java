package gnete.card.dao;

import flink.util.Paginater;
import gnete.card.entity.CardInfo;

import java.util.List;
import java.util.Map;

public interface UnLossCardRegDAO extends BaseDAO {
	public Paginater findUnLossCard(Map<String, Object> params, int pageNumber,
			int pageSize);
	
	public List<CardInfo> findCardInfo(Map<String, Object> params);
	
	public int updateCardInfo(CardInfo cardInfo);
	
	/**
	 * 查找批量解挂记录
	 * @param params
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Paginater findUnLossCardBat(Map<String, Object> params,
			int pageNumber, int pageSize);
	
}