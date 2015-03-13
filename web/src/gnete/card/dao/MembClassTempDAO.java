package gnete.card.dao;

import flink.util.Paginater;
import gnete.card.entity.MembClassTemp;

import java.util.List;
import java.util.Map;

public interface MembClassTempDAO extends BaseDAO {
	/**
	 * 查询会员类型模板列表
	 * @param params
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Paginater findMembClassTemp(Map<String, Object> params, int pageNumber,int pageSize);
	/**
	 * 查询积分类型模板列表
	 * @param params
	 * @return
	 */
	List<MembClassTemp> findList(Map<String, Object> params);
	
	
	
}