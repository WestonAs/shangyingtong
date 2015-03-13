package gnete.card.dao;

import gnete.card.entity.Waitsinfo;
import java.util.List;
import java.util.Map;

public interface WaitsinfoDAO extends BaseDAO {

	/**
	 * 查找待处理的web任务
	 * @return
	 */
	List<Waitsinfo> findUndoForWeb();
	
	/**
	 * 查找待处理的web任务(加入了分页的处理)
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	List<Waitsinfo> findUndoForWebPage(int pageNum, int pageSize);
	
	/**
	 * 根据msgType和refId找到对应的waitsinfo对象
	 * @param msgType
	 * @param refId
	 * @return
	 */
	Waitsinfo findWaitsinfo(String msgType, Long refId);
	
	/**
	 * 根据msgType和refId更新webState字段
	 * @param params
	 * @return
	 */
	int updateWebStatus(Map<String, Object> params);
}