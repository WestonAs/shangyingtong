package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import gnete.card.dao.ReportResourceDAO;
import gnete.etc.BizException;

@Repository
public class ReportResourceDAOImpl extends BaseDAOIbatisImpl implements ReportResourceDAO {

	@Override
	protected String getNamespace() {
		return "ReportResource";
	}
	
	public List<Map<String, Object>> findOptDetailPreFund(
			Map<String, Object> params) throws BizException {
		return this.queryForList("findOptDetailPreFund", params);
	}

	public List<Map<String, Object>> findOptDetailAccuTimeAvail(
			Map<String, Object> params) throws BizException {
		return this.queryForList("findOptDetailAccuTimeAvail", params);
	}

	public List<Map<String, Object>> findOptDetailCouponFund(
			Map<String, Object> params) throws BizException {
		return this.queryForList("findOptDetailCouponFund", params);
	}

	public List<Map<String, Object>> findOptDetailSpePointTransNum(
			Map<String, Object> params) throws BizException {
		return this.queryForList("findOptDetailSpePointTransNum", params);
	}

	public List<Map<String, Object>> findOptDetailUniPointFund(
			Map<String, Object> params) throws BizException {
		return this.queryForList("findOptDetailUniPointFund", params);
	}

	public List<Map<String, Object>> findOptDetailDisCountMembNum(
			Map<String, Object> params) throws BizException {
		return this.queryForList("findOptDetailDisCountMemb", params);
		
	}

	public List<Map<String, Object>> findOptDetailMembNum(
			Map<String, Object> params) throws BizException {
		return this.queryForList("findOptDetailMemb", params);
	}

	public List<Map<String, Object>> findAgentFeeShareOpt(
			Map<String, Object> params) throws BizException {
		return this.queryForList("findAgentFeeShareOpt", params);
	}
	
	public List<Map<String, Object>> findAgentFeeShareConsum(
			Map<String, Object> params) throws BizException {
		return this.queryForList("findAgentFeeShareConsum", params);
	}
	
	public List<Map<String, Object>> findAgentFeeSharePoint(
			Map<String, Object> params) throws BizException {
		return this.queryForList("findAgentFeeSharePoint", params);
	}
	
	public List<Map<String, Object>> findAgentFeeShareAccu(
			Map<String, Object> params) throws BizException {
		return this.queryForList("findAgentFeeShareAccu", params);
	}
	
	public List<Map<String, Object>> findAgentFeeSharePointExcGift(
			Map<String, Object> params) throws BizException {
		return this.queryForList("findAgentFeeSharePointExcGift", params);
	}
	
	public List<Map<String, Object>> findAgentFeeShareSaleCardRecharge(
			Map<String, Object> params) throws BizException {
		return this.queryForList("findAgentFeeShareSaleCardRecharge", params);
	}
	

}

