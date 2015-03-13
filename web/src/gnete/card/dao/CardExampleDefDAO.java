package gnete.card.dao;

import java.util.List;
import java.util.Map;

import flink.util.Paginater;
import gnete.card.entity.CardExampleDef;

/**
 * @File: CardExampleDefDAO.java
 *
 * @description: 卡样定义表操作DAO
 *
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @modify:
 * @version: 1.0
 * @since 1.0 2012-2-7
 */
public interface CardExampleDefDAO extends BaseDAO {
	
	/**
	 * 查找卡样信息
	 * 
	 * @param params	查询参数信息
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater findPage(Map<String, Object> params, int pageNumber, int pageSize);
	
	/**
	 * 查找卡样信息(选择器中用到的)
	 * 
	 * @param params	查询参数信息
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater findSelectPage(Map<String, Object> params, int pageNumber, int pageSize);
	
	/**
	 * 查找卡样列表
	 * 
	 * @param params
	 * @return
	 */
	List<CardExampleDef> findList(Map<String, Object> params);
	
	/**
	 * 同一运营分支机构的卡样名不能重复 
	 * 卡样名称是否已经存在
	 * @param styleName
	 * @return
	 */
	boolean isExsitStyleName(String styleName, String branchCode);
}