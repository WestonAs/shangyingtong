package gnete.card.dao;

import flink.util.Paginater;
import gnete.card.entity.SubAcctBal;

import java.util.Map;

public interface UnfreezeRegDAO extends BaseDAO {

	Paginater findUnfreezeWithMultiParms(Map<String, Object> params,
			int pageNumber, int pageSize);

	Paginater findCardInfo(Map<String, Object> params, int pageNumber,
			int defaultSelectPageSize);
	
	public Object findCardInfo(Map<String, Object> params);

	SubAcctBal findAmt(Map<String, Object> params);
	
	/**
	 * 查找批量解付记录
	 * @param params
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Paginater findUnfreezeBat(Map<String, Object> params,
			int pageNumber, int pageSize);
}