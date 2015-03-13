package gnete.card.dao;

import java.util.List;
import java.util.Map;

import flink.util.Paginater;
import gnete.card.entity.CardPrevConfig;

/**
 * @File: CardPrevConfigDAO.java
 *
 * @description: 发卡机构卡前三位配置，对发卡机构做配置的
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @modify:
 * @version: 1.0
 * @since 1.0 2012-5-9 上午10:34:05
 */
public interface CardPrevConfigDAO extends BaseDAO {
	
	/**
	 * 分页页面
	 * @param params
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater findCardPrevConfigPage(Map<String, Object> params, int pageNumber, int pageSize);
	
	/**
	 * 根据卡前三位查询
	 * @param cardPrev
	 * @return
	 */
	List<CardPrevConfig> findByCardPrev(String cardPrev);
}