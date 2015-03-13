package gnete.card.dao;

import flink.util.Paginater;
import gnete.card.entity.PointBal;
import java.util.List;
import java.util.Map;

public interface PointBalDAO extends BaseDAO {
	public List findPointBal(Map<String, Object> params);
	
	/**
	 * 找出积分帐户表中"可用积分"大于积分子类型表中"参考积分"的记录
	 * @param params
	 * @return
	 */
	public List findPointBalAval(Map<String, Object> params);
	
	/**
	 * 根据帐号和联名机构编号查找可用积分类型
	 * @param params
	 * @return
	 */
	public List<PointBal> getPointBalList(Map<String, Object> params);
	
	public Paginater getPointBalList(Map<String, Object> params, int pageNumber,
			int pageSize);
}