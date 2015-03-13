package gnete.card.dao;

import flink.util.Paginater;
import gnete.card.entity.ReleaseCardFee;

import java.util.List;
import java.util.Map;

public interface ReleaseCardFeeDAO extends BaseDAO {
	public Paginater findReleaseCardFee(Map<String, Object> params, int pageNumber, int pageSize);

	public ReleaseCardFee getReleaseCardFee(Map<String, Object> params);

	List<ReleaseCardFee> getFeeListByBranch(Map<String, Object> params);

	/** 根据条件， 查找可用的ReleaseCardFee记录 */
	public List<ReleaseCardFee> findReleaseCardFeeList(String branchCode, String cardBin, String merchId,
			String transType);

}