package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.OldReportParaDAO;
import gnete.card.entity.OldReportPara;
import gnete.card.entity.type.IssType;

@Repository
public class OldReportParaDAOImpl extends BaseDAOIbatisImpl implements OldReportParaDAO {

    public String getNamespace() {
        return "OldReportPara";
    }

	public Paginater findOldReportPara(Map<String, Object> params,
			int pageNumber, int pageSize) {
		return this.queryForPage("findOldReportPara", params, pageNumber, pageSize);
	}
	
	public List<OldReportPara> findByIssType(IssType issType) {
		return this.queryForList("findByIssType", issType.getValue());
	}
}