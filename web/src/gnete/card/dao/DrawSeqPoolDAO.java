package gnete.card.dao;

import gnete.card.entity.DrawSeqPool;

import java.util.List;

/**
 * @File: DrawSeqPoolDAO.java
 *
 * @description: 抽奖活动Sequence池操作类
 *
 * @copyright: (c) 2008 YLINK INC.
 * @author: aps-zwi
 * @version: 1.0
 * @since 1.0 2010-9-30
 */
public interface DrawSeqPoolDAO extends BaseDAO {
	
	/**
	 * 查询状态有效的Sequence列表
	 * @param status
	 * @return
	 */
	List<DrawSeqPool> findEffectSeq(String status);
}