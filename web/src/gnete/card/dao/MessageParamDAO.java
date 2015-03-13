package gnete.card.dao;

import java.util.List;
import java.util.Map;
import flink.util.Paginater;
import gnete.card.entity.MessageParam;

public interface MessageParamDAO extends BaseDAO {
	
	public Paginater findMessageParam(Map<String, Object> params, int pageNumber,
			int pageSize);
	
	List<MessageParam> getMessageParamList(Map<String, Object> params);
	
	/**
	 * 
	  * @description：
	  * @param params
	  * @return  
	  * @version: 2011-4-19 上午10:21:21
	  * @See:
	 */
	List<MessageParam> findMessageParam(Map params);
}