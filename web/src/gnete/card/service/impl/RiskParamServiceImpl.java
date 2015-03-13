package gnete.card.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import gnete.card.dao.RiskParamDAO;
import gnete.card.entity.RiskParam;
import gnete.card.entity.RiskParamKey;
import gnete.card.service.RiskParamService;
import gnete.etc.Assert;
import gnete.etc.BizException;

@Service("riskParamService")
public class RiskParamServiceImpl implements RiskParamService {
	@Autowired
	private RiskParamDAO riskParamDAO;

	public boolean deleteRiskParamKey(RiskParamKey key) throws BizException {
		
		return false;
	}

	public boolean modifyRiskParam(RiskParam riskParam, String modifyUserId)
			throws BizException {
		Assert.notNull(riskParam, "更新的卡BIN风险监控参数对象不能为空");		
		
		riskParam.setPreGuard(riskParam.getResistance());
		riskParam.setUpdateTime(new Date());
		riskParam.setUpdateUser(modifyUserId);
		
		int count = this.riskParamDAO.update(riskParam);		
		return count > 0;
	}
	

}
