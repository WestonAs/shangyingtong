package gnete.card.service;

import gnete.card.entity.RiskParam;
import gnete.card.entity.RiskParamKey;
import gnete.etc.BizException;

public interface RiskParamService {
	
	/**
	 * 修改卡BIN风险监控参数
	 * @param riskParam	卡BIN风险监控信息
	 * @param string 
	 * @return
	 * @throws BizException
	 */
	boolean modifyRiskParam(RiskParam riskParam, String modifyUserId) throws BizException ;
	
	/**
	 * 删除卡BIN风险监控参数
	 * @param string 
	 * @return
	 * @throws BizException
	 */
	boolean deleteRiskParamKey(RiskParamKey key) throws BizException ;
}
