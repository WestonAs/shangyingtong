package gnete.card.dao;

import java.util.List;
import java.util.Map;

import flink.util.Paginater;
import gnete.card.entity.ChlFeeTemplate;

public interface ChlFeeTemplateDAO extends BaseDAO {
	
	Paginater findPage(Map<String, Object> params, int pageNumber, int pageSize);
	
	List<ChlFeeTemplate> findList(Map<String, Object> params);
	
	List<ChlFeeTemplate> findDistinctList();
	
	Long getSeq();
}