package gnete.card.dao;

import flink.util.Paginater;
import gnete.card.entity.CardTypeCode;

import java.util.List;
import java.util.Map;

public interface CardTypeCodeDAO extends BaseDAO {
	
	/**
	 * 查找状态有效的卡类型
	 * 
	 * @param status 状态
	 * @return
	 */
	List<CardTypeCode> findCardTypeCode(String status);
	
	public Paginater find(Map<String, Object> params, int pageNumber,
    		int pageSize);
}