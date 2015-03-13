package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.MerchGroupDAO;
import gnete.card.entity.MerchGroup;

@Repository
public class MerchGroupDAOImpl extends BaseDAOIbatisImpl implements MerchGroupDAO {

	public String getNamespace() {
		return "MerchGroup";
	}

	public Paginater findPage(Map<String, Object> params, int pageNumber, int pageSize) {
		return this.queryForPage("findMerchGroup", params, pageNumber, pageSize);
	}
	
	public List<MerchGroup> findList(Map<String, Object> params) {
		return this.queryForList("findMerchGroup", params);
	}
	
	public String selectMerchGroupSEQ() {
		return (String)this.queryForObject("selectMerchGroupSEQ");
	}
}