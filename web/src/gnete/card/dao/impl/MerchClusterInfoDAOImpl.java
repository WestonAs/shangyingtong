package gnete.card.dao.impl;

import flink.util.Paginater;
import gnete.card.dao.MerchClusterInfoDAO;
import gnete.card.entity.MerchClusterInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class MerchClusterInfoDAOImpl extends BaseDAOIbatisImpl implements MerchClusterInfoDAO {

	public String getNamespace() {
		return "MerchClusterInfo";
	}

	public Paginater findPage(Map<String, Object> params, int pageNumber, int pageSize) {
		return this.queryForPage("findMerchClusterInfo", params, pageNumber, pageSize);
	}

	@Override
	public List<MerchClusterInfo> findByMerchClusterName(Map<String, Object> params) {
		List<MerchClusterInfo> merchClusterInfos = this.queryForList("findByMerchClusterName", params);
		return merchClusterInfos.size() > 0 ? merchClusterInfos : null;
	}

	public List<MerchClusterInfo> findMerchClusterInfo(Map<String, Object> params) {
		return this.queryForList("findMerchClusterInfo", params);
	}

	@Override
	public List<MerchClusterInfo> findByCardIssuer(String cardIssuer) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cardIssuer", cardIssuer);
		return this.findMerchClusterInfo(params);
	}
}