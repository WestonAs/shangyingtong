package gnete.card.dao.impl;

import flink.util.Paginater;
import gnete.card.dao.ReleaseCardFeeDAO;
import gnete.card.entity.ReleaseCardFee;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class ReleaseCardFeeDAOImpl extends BaseDAOIbatisImpl implements ReleaseCardFeeDAO {

    public String getNamespace() {
        return "ReleaseCardFee";
    }

	public Paginater findReleaseCardFee(Map<String, Object> params,
			int pageNumber, int pageSize) {
		return this.queryForPage("findReleaseCardFee", params, pageNumber, pageSize);
	}

	public List<ReleaseCardFee> getFeeListByBranch(Map<String, Object> params) {
		return this.queryForList("getFeeListByBranch", params);
	}

	public ReleaseCardFee getReleaseCardFee(Map<String, Object> params) {
		return (ReleaseCardFee) this.queryForObject("getReleaseCardFee", params);
	}
	
	@Override
	public List<ReleaseCardFee> findReleaseCardFeeList(String branchCode, String cardBin, String merchId,
			String transType) {
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("branchCode", branchCode);
		params.put("cardBin", cardBin);
		params.put("merchId", merchId);
		params.put("transType", transType);
		return this.queryForList("findReleaseCardFeeList", params);
	}
}