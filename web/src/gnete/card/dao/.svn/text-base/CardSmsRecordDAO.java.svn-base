package gnete.card.dao;

import flink.util.Paginater;

import java.util.Map;

/**
 * 短信发送记录DAO
 */
public interface CardSmsRecordDAO extends BaseDAO {

	Paginater findByPage(Map<String, Object> params, int pageNumber,
			int pageSize);

	/** 是否是授权的发卡机构（有发送短信的权限）*/
	boolean isAuthorizedBranch(String cardBranchCode);
}