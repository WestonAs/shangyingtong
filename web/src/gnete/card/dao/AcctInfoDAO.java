package gnete.card.dao;

import java.util.List;
import java.util.Map;
import flink.util.Paginater;
import gnete.card.entity.AcctInfo;

public interface AcctInfoDAO extends BaseDAO {
	
	public Paginater findAcctInfo(Map<String, Object> params, int pageNumber,
			int pageSize);

	/**
	 * 根据条件查询卡账户信息
	 * @param params
	 * @return
	 */
	List<AcctInfo> findAcctInfoList(Map<String, Object> params);
	
	// 根据卡号找关联的帐户信息
	public AcctInfo findAcctInfoByCardId(String cardId);
	
	public AcctInfo findByPkWithCheck(Map<String, Object> params);
			
}