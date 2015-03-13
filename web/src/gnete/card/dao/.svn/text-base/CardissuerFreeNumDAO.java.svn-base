package gnete.card.dao;

import java.util.List;
import java.util.Map;

import flink.util.Paginater;
import gnete.card.entity.CardissuerFreeNum;

/**
 * @File: CardissuerFreeNumDAO.java
 *
 * @description: 发卡机构赠送卡数量表操作DAO
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @modify:
 * @version: 1.0
 * @since 1.0 2012-8-23 下午02:48:03
 */
public interface CardissuerFreeNumDAO extends BaseDAO {
	
	/**
	 * 查询发卡机构赠送卡数量表列表页面
	 * 
	 * @param params
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater findPage(Map<String, Object> params, int pageNumber, int pageSize);
	
	/**
	 * 查询发卡机构赠送卡数量表列表
	 * 
	 * @param params
	 * @return
	 */
	List<CardissuerFreeNum> findList(Map<String, Object> params);
}