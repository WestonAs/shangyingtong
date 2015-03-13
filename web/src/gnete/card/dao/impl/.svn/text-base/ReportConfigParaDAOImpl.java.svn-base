package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import flink.util.Paginater;
import gnete.card.dao.ReportConfigParaDAO;
import gnete.card.entity.ReportConfigPara;

@Repository
public class ReportConfigParaDAOImpl extends BaseDAOIbatisImpl implements ReportConfigParaDAO {

    public String getNamespace() {
        return "ReportConfigPara";
    }

	public Paginater findReportConfigPara(Map<String, Object> params,
			int pageNumber, int pageSize) {
		return this.queryForPage("findReportConfigPara", params, pageNumber, pageSize);
	}

	public List<ReportConfigPara> getReportConfigParaList(Map<String, Object> params) {
		return this.queryForList("getReportConfigParaList", params);
	}
	
	
}