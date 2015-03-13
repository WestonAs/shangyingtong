package gnete.card.dao;

import java.util.List;
import java.util.Map;

import flink.util.Paginater;
import gnete.card.entity.PublishNotice;

/**
 * @File: PublishNoticeDAO.java
 * 
 * @description: 发布通知信息
 * 
 * @copyright: (c) 2010 YLINK INC.
 * @author: ZhaoWei
 * @version: 1.0
 * @since 1.0 2010-11-18
 */
public interface PublishNoticeDAO extends BaseDAO {

	/**
	 * 查询通知信息列表
	 * 
	 * @param params
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater findPublishNotice(Map<String, Object> params, int pageNumber,
			int pageSize);

	/**
	 * 更新表中数据的showFlag字段
	 * 
	 * @param showFlag
	 * @return
	 */
	int updateShowFlag(String showFlag);

	/**
	 * 根据显示标志查找通知信息表
	 * 
	 * @param showFlag
	 * @return
	 */
	List<PublishNotice> findByShowFlag(String showFlag);
}