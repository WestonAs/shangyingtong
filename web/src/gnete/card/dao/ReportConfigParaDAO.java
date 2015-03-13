package gnete.card.dao;

import java.util.List;
import java.util.Map;
import flink.util.Paginater;
import gnete.card.entity.ReportConfigPara;

public interface ReportConfigParaDAO extends BaseDAO {
	Paginater findReportConfigPara(Map<String, Object> params, int pageNumber,
			int pageSize);
	
	List<ReportConfigPara> getReportConfigParaList(Map<String, Object> params);
}