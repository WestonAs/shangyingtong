package gnete.card.dao.impl;

import flink.util.Paginater;
import gnete.card.dao.TransDAO;
import gnete.card.entity.Trans;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class TransDAOImpl extends BaseDAOIbatisImpl implements TransDAO {

    public String getNamespace() {
        return "Trans";
    }

	public Paginater findTrans(Map<String, Object> params, int pageNumber,
			int pageSize) {
		return this.queryForPage("findTrans", params, pageNumber, pageSize);
	}

	public Paginater findTransJFLINK(Map<String, Object> params,
			int pageNumber, int pageSize) {
		return this.queryForPage("findTransJFLINK", params, pageNumber, pageSize);
	}
    
	public Paginater findMerchTrans(Map<String, Object> params, int pageNumber,
			int pageSize) {
		return this.queryForPage("findMerchTrans", params, pageNumber, pageSize);
	}

	public Trans findByPkWithCheck(Map<String, Object> params) {
		return (Trans) this.queryForObject("findByPkWithCheck", params);
	}
    
	public List<Map<String, Object>> findExpireCardTransReport(
			Map<String, Object> params) {
		return this.queryForList("findExpireCardTransReport", params);
	}
	
	public List<Map<String, Object>> findExpireTransTermrlSumReport(
			Map<String, Object> params) {
		return this.queryForList("findExpireTransTermrlSumReport", params);
	}
	
	public List<Map<String, Object>> findExpireTransSumReport(
			Map<String, Object> params) {
		return this.queryForList("findExpireTransSumReport", params);
	}
	
	public List<Map<String, Object>> findActiveCardReport(
			Map<String, Object> params) {
		return this.queryForList("findActiveCardReport", params);
	}

	public List<Map<String, Object>> findMerchDetailReport(Map<String, Object> params) {
		return (List<Map<String, Object>>) this.queryForList("findMerchDetailReport", params);
	}

	public List<Map<String, Object>> findTerminalSubTotalReport(Map<String, Object> params) {
		return (List<Map<String, Object>>) this.queryForList("findTerminalSubTotalReport", params);
	}

	public List<Map<String, Object>> findMerchDetailSumReport(Map<String, Object> params) {
		return (List<Map<String, Object>>) this.queryForList("findMerchDetailSumReport", params);
	}

	public Trans findByPkFromJFLINK(Map<String, Object> params) {
		return (Trans) this.queryForObject("findByPkFromJFLINK", params);
	}

	public Paginater findHisTrans(Map<String, Object> params, int pageNumber,
			int pageSize) {
		return this.queryForPage("findHisTrans", params, pageNumber, pageSize);
	}
	
	public List<Trans> findHisTransList(Map<String, Object> params) {
		return this.queryForList("findHisTrans", params);
	}

	public Paginater findHisRiskTrans(Map<String, Object> params, int pageNumber,
			int pageSize) {
		return this.queryForPage("findHisRiskTrans", params, pageNumber, pageSize);
	}
	
	public List<Trans> findHisRiskTransList(Map<String, Object> params) {
		return this.queryForList("findHisRiskTrans", params);
	}
	
	public Paginater findTransMonitor(Map<String, Object> params, int pageNumber, int pageSize) {
		return this.queryForPage("findTransMonitor", params, pageNumber, pageSize);
	}
	
	public String getLastTransDate(Map<String, Object> params) {
		return (String)this.queryForObject("getLastTransDate", params);
	}
}