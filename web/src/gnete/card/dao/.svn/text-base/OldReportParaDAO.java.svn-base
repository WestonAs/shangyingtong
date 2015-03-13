package gnete.card.dao;

import java.util.List;
import java.util.Map;

import flink.util.Paginater;
import gnete.card.entity.OldReportPara;
import gnete.card.entity.type.IssType;

public interface OldReportParaDAO extends BaseDAO {
	
	Paginater findOldReportPara(Map<String, Object> params, int pageNumber,
			int pageSize);
	
	/**
	 * 根据机构类型查询需要生成旧报表的机构
	 * @param issType
	 * @return
	 */
	List<OldReportPara> findByIssType(IssType issType);
}