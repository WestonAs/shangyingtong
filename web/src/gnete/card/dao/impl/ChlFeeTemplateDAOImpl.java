package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.ChlFeeTemplateDAO;
import gnete.card.entity.ChlFeeTemplate;

@Repository
public class ChlFeeTemplateDAOImpl extends BaseDAOIbatisImpl implements ChlFeeTemplateDAO {

	public String getNamespace() {
		return "ChlFeeTemplate";
	}

	public Paginater findPage(Map<String, Object> params, int pageNumber, int pageSize) {
		return this.queryForPage("findChlFeeTemplate", params, pageNumber, pageSize);
	}
	
	public List<ChlFeeTemplate> findList(Map<String, Object> params) {
		return this.queryForList("findChlFeeTemplate", params);
	}
	
	public List<ChlFeeTemplate> findDistinctList() {
		return this.queryForList("findDistinctList");
	}
	
	public Long getSeq() {
		return (Long)this.queryForObject("getSeq");
	}
}