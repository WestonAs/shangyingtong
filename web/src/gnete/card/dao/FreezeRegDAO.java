package gnete.card.dao;

import flink.util.Paginater;
import gnete.card.entity.SubAcctBal;

import java.util.Map;

public interface FreezeRegDAO extends BaseDAO {

	public Paginater findFreezeWithMultiParms(Map<String, Object> params, int pageNumber,
			int pageSize);

	public Paginater findCardInfo(Map<String, Object> params, int pageNumber,
			int defaultSelectPageSize);

	public SubAcctBal findAmt(Map<String, Object> params);
	
	public Object findCardInfo(Map<String, Object> params);
	
	/**
	 * 查找批量冻结记录
	 * @param params
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Paginater findFreezeBat(Map<String, Object> params,
			int pageNumber, int pageSize);
}