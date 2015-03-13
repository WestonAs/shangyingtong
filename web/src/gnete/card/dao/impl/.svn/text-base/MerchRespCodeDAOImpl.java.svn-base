package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.MerchRespCodeDAO;
import gnete.card.entity.MerchRespCode;

@Repository
public class MerchRespCodeDAOImpl extends BaseDAOIbatisImpl implements MerchRespCodeDAO {

	public String getNamespace() {
		return "MerchRespCode";
	}

	public Paginater findPage(Map<String, Object> params, int pageNumber, int pageSize) {
		return this.queryForPage("findMerchRespCode", params, pageNumber, pageSize);
	}
	
	public List<MerchRespCode> findList(Map<String, Object> params) {
		return this.queryForList("findMerchRespCode", params);
	}
}