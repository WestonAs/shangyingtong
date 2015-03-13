package gnete.card.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.ReportPathSaveDAO;
import gnete.card.entity.ReportPathSave;
import gnete.card.entity.ReportPathSaveKey;

@Repository
public class ReportPathSaveDAOImpl extends BaseDAOIbatisImpl implements ReportPathSaveDAO {

	public String getNamespace() {
		return "ReportPathSave";
	}

	public Paginater findPage(Map<String, Object> params, int pageNumber, int pageSize) {
		return this.queryForPage("findPage", params, pageNumber, pageSize);
	}

	public boolean deleteByMap(Map<String, Object> params) {
		return this.delete("deleteByMap", params) > 0;
	}

	@Override
	public ReportPathSave findByPk(String reportType, String merchantNo, String reportDate) {
		ReportPathSaveKey key = new ReportPathSaveKey(reportType, merchantNo, reportDate);
		return (ReportPathSave) this.findByPk(key);
	}

}