package gnete.card.dao;

import flink.util.NameValuePair;
import flink.util.Paginater;
import gnete.card.entity.Area;

import java.util.List;
import java.util.Map;

public interface AreaDAO extends BaseDAO {

	Paginater find(Map<String, Object> params, int pageNumber, int pageSize);

	/**
	 * 查找父类型为-1的城市
	 * 
	 * @return
	 */
	List<NameValuePair> findParent();
	
	/**
	 * 根据省份查找地级市
	 * @param parent
	 * @return
	 */
	List<NameValuePair> findCityByParent(String parent);

	List<Area> findAll(Map<String, Object> params);
}