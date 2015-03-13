package gnete.card.dao;

import flink.util.Paginater;
import gnete.card.entity.CardDeferReg;

import java.util.List;
import java.util.Map;

public interface CardDeferRegDAO extends BaseDAO {

//	Paginater findCardDeferWithMultiParms(Map<String, Object> params, int pageNumber, int pageSize);
	
//	Paginater findCardDeferBat(Map<String, Object> params, int pageNumber, int pageSize);
	
	/**
	 * 查找卡延期列表页面（单笔和批量）
	 * @param params
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater findCardDeferPage(Map<String, Object> params, int pageNumber, int pageSize);
	
	/**
	 * 根据指定条件查询卡延期列表数据
	 * @param params
	 * @return
	 */
	List<CardDeferReg> findCardDeferList(Map<String, Object> params);
	
	List<CardDeferReg> findCardDeferCheckList(Map<String, Object> params);
	
}